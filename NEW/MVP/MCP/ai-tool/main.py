"""
AI Tool MCP (8774) - Blind AI Executor

Endpoints:
- POST /extract - Extract structured data from text
- POST /chat - AI chat with tool support
- GET /health - Health check
"""
import json
import time
from typing import Any, Dict, List, Optional

import httpx
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

import config


# === Request/Response Models ===

class ExtractionRequest(BaseModel):
    """Request for structured data extraction"""
    message: str = Field(..., description="User message to extract from")
    extraction_schema: Dict[str, Any] = Field(..., description="JSON schema for extraction")
    system_prompt: Optional[str] = Field(None, description="Optional system prompt override")
    model: Optional[str] = Field(None, description="Model to use (default: qwen3-30b)")


class ExtractionResponse(BaseModel):
    success: bool
    data: Optional[Dict[str, Any]] = None
    raw_response: Optional[str] = None
    error: Optional[str] = None
    execution_time_ms: int
    model_used: str


class ChatMessage(BaseModel):
    role: str  # "system", "user", "assistant"
    content: str


class ToolDefinition(BaseModel):
    name: str
    description: str
    parameters: Dict[str, Any]


class ChatRequest(BaseModel):
    """Request for AI chat with optional tools"""
    messages: List[ChatMessage]
    tools: Optional[List[ToolDefinition]] = None
    model: Optional[str] = Field(None, description="Model to use (default: claude-3-5-sonnet)")
    max_tokens: Optional[int] = Field(4096, description="Max tokens in response")
    temperature: Optional[float] = Field(0.7, description="Temperature 0-1")


class ToolCall(BaseModel):
    name: str
    arguments: Dict[str, Any]


class ChatResponse(BaseModel):
    success: bool
    content: Optional[str] = None
    tool_calls: Optional[List[ToolCall]] = None
    error: Optional[str] = None
    execution_time_ms: int
    model_used: str
    usage: Optional[Dict[str, int]] = None


# === FastAPI App ===

app = FastAPI(
    title="AI Tool MCP",
    description="Blind AI executor for extraction and chat",
    version="1.0.0"
)


# === Helper Functions ===

async def call_openrouter(
    messages: List[Dict],
    model: str,
    tools: Optional[List[Dict]] = None,
    max_tokens: int = 4096,
    temperature: float = 0.7
) -> Dict:
    """Call OpenRouter API"""
    headers = {
        "Authorization": f"Bearer {config.OPENROUTER_API_KEY}",
        "Content-Type": "application/json",
        "HTTP-Referer": "https://eldoleado.ru",
        "X-Title": "ELO AI Tool"
    }

    payload = {
        "model": model,
        "messages": messages,
        "max_tokens": max_tokens,
        "temperature": temperature
    }

    if tools:
        payload["tools"] = tools
        payload["tool_choice"] = "auto"

    async with httpx.AsyncClient(timeout=60.0) as client:
        response = await client.post(
            f"{config.OPENROUTER_BASE_URL}/chat/completions",
            headers=headers,
            json=payload
        )
        response.raise_for_status()
        return response.json()


def build_extraction_prompt(message: str, schema: Dict) -> str:
    """Build extraction prompt with schema"""
    schema_str = json.dumps(schema, ensure_ascii=False, indent=2)
    return f"""Extract information from the user message according to the schema below.
Return ONLY valid JSON matching the schema. No explanations.

SCHEMA:
{schema_str}

USER MESSAGE:
{message}

JSON OUTPUT:"""


# === Endpoints ===

@app.get("/health")
async def health():
    """Health check"""
    has_key = bool(config.OPENROUTER_API_KEY)
    return {
        "status": "ok" if has_key else "warning",
        "openrouter_configured": has_key,
        "default_extraction_model": config.DEFAULT_EXTRACTION_MODEL,
        "default_chat_model": config.DEFAULT_CHAT_MODEL
    }


@app.post("/extract", response_model=ExtractionResponse)
async def extract(req: ExtractionRequest):
    """
    Extract structured data from text.

    Uses extraction_schema to define what to extract.
    Returns JSON matching the schema.
    """
    start = time.time()
    model = req.model or config.DEFAULT_EXTRACTION_MODEL

    if not config.OPENROUTER_API_KEY:
        return ExtractionResponse(
            success=False,
            error="OpenRouter API key not configured",
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )

    # Build prompt
    prompt = build_extraction_prompt(req.message, req.extraction_schema)

    # System prompt
    system = req.system_prompt or "You are a precise data extraction assistant. Extract ONLY the requested fields. Return valid JSON."

    messages = [
        {"role": "system", "content": system},
        {"role": "user", "content": prompt}
    ]

    try:
        response = await call_openrouter(
            messages=messages,
            model=model,
            max_tokens=2048,
            temperature=0.1  # Low temperature for extraction
        )

        raw_content = response["choices"][0]["message"]["content"]

        # Try to parse JSON from response
        # Handle markdown code blocks
        content = raw_content.strip()
        if content.startswith("```json"):
            content = content[7:]
        if content.startswith("```"):
            content = content[3:]
        if content.endswith("```"):
            content = content[:-3]
        content = content.strip()

        try:
            extracted = json.loads(content)
            return ExtractionResponse(
                success=True,
                data=extracted,
                raw_response=raw_content,
                execution_time_ms=int((time.time() - start) * 1000),
                model_used=model
            )
        except json.JSONDecodeError as e:
            return ExtractionResponse(
                success=False,
                error=f"Failed to parse JSON: {e}",
                raw_response=raw_content,
                execution_time_ms=int((time.time() - start) * 1000),
                model_used=model
            )

    except httpx.HTTPStatusError as e:
        return ExtractionResponse(
            success=False,
            error=f"API error: {e.response.status_code} - {e.response.text}",
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )
    except Exception as e:
        return ExtractionResponse(
            success=False,
            error=str(e),
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )


@app.post("/chat", response_model=ChatResponse)
async def chat(req: ChatRequest):
    """
    AI chat with optional tool support.

    If tools are provided, model can return tool_calls.
    """
    start = time.time()
    model = req.model or config.DEFAULT_CHAT_MODEL

    if not config.OPENROUTER_API_KEY:
        return ChatResponse(
            success=False,
            error="OpenRouter API key not configured",
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )

    # Convert messages to dict
    messages = [{"role": m.role, "content": m.content} for m in req.messages]

    # Convert tools to OpenAI format
    tools = None
    if req.tools:
        tools = [
            {
                "type": "function",
                "function": {
                    "name": t.name,
                    "description": t.description,
                    "parameters": t.parameters
                }
            }
            for t in req.tools
        ]

    try:
        response = await call_openrouter(
            messages=messages,
            model=model,
            tools=tools,
            max_tokens=req.max_tokens or 4096,
            temperature=req.temperature or 0.7
        )

        choice = response["choices"][0]
        message = choice["message"]

        # Extract content
        content = message.get("content")

        # Extract tool calls
        tool_calls = None
        if message.get("tool_calls"):
            tool_calls = []
            for tc in message["tool_calls"]:
                func = tc.get("function", {})
                args = func.get("arguments", "{}")
                if isinstance(args, str):
                    try:
                        args = json.loads(args)
                    except json.JSONDecodeError:
                        args = {}
                tool_calls.append(ToolCall(
                    name=func.get("name", ""),
                    arguments=args
                ))

        # Usage stats
        usage = response.get("usage")

        return ChatResponse(
            success=True,
            content=content,
            tool_calls=tool_calls,
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model,
            usage=usage
        )

    except httpx.HTTPStatusError as e:
        return ChatResponse(
            success=False,
            error=f"API error: {e.response.status_code} - {e.response.text}",
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )
    except Exception as e:
        return ChatResponse(
            success=False,
            error=str(e),
            execution_time_ms=int((time.time() - start) * 1000),
            model_used=model
        )


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=config.HOST, port=config.PORT)
