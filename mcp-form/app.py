"""Form Submission REST API Server.

FastAPI server for handling web form submissions (quizzes, contact forms, etc.)
and forwarding them to n8n for processing.
"""

import logging
from typing import Optional, List, Dict, Any
from datetime import datetime
from contextlib import asynccontextmanager
import uuid

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header
from fastapi.responses import JSONResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    logger.info("Form API server started")
    yield
    logger.info("Form API server stopped")


app = FastAPI(
    title="Form Submission API",
    description="REST API for web form submissions (quizzes, contact forms) integration with n8n",
    version="1.0.0",
    lifespan=lifespan
)

# CORS - allow all origins for form submissions from any website
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ========== Pydantic Models ==========

class QuizAnswer(BaseModel):
    """Single quiz answer."""
    question: str = Field(..., description="Question text")
    answer: str = Field(..., description="Answer value")
    question_id: Optional[str] = Field(None, description="Question ID")


class FormSubmission(BaseModel):
    """Web form submission data."""
    # Required fields
    phone: str = Field(..., description="Client phone number")

    # Optional client info
    name: Optional[str] = Field(None, description="Client name")
    email: Optional[str] = Field(None, description="Client email")

    # Form identification
    form_id: Optional[str] = Field(None, description="Form identifier (e.g., 'quiz_battery', 'contact_form')")
    form_name: Optional[str] = Field(None, description="Human-readable form name")

    # Quiz data
    quiz_answers: Optional[List[QuizAnswer]] = Field(None, description="Quiz answers if this is a quiz form")

    # Free-form data
    custom_data: Optional[Dict[str, Any]] = Field(None, description="Any additional custom data")

    # Message/comment
    message: Optional[str] = Field(None, description="Free text message or comment")

    # Source tracking
    utm_source: Optional[str] = Field(None, description="UTM source")
    utm_medium: Optional[str] = Field(None, description="UTM medium")
    utm_campaign: Optional[str] = Field(None, description="UTM campaign")
    utm_content: Optional[str] = Field(None, description="UTM content")
    utm_term: Optional[str] = Field(None, description="UTM term")
    referrer: Optional[str] = Field(None, description="Referrer URL")
    landing_page: Optional[str] = Field(None, description="Landing page URL")

    # Tenant info (for multi-tenant setup)
    tenant_id: Optional[str] = Field(None, description="Tenant ID")


class ApiResponse(BaseModel):
    success: bool
    data: Optional[dict] = None
    error: Optional[str] = None
    submission_id: Optional[str] = None


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


async def verify_api_key_optional(x_api_key: Optional[str] = Header(None)):
    """Optional API key verification - for public form endpoints."""
    # Public endpoints don't require API key
    return True


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "form-submission-api",
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "form-submission-api",
        "version": "1.0.0",
        "n8n_configured": bool(settings.n8n_webhook_url)
    }


# ========== Form Submission Endpoints ==========

@app.post("/api/submit", dependencies=[Depends(verify_api_key_optional)])
async def submit_form(submission: FormSubmission, request: Request):
    """Submit a web form.

    This is the main endpoint for form submissions from websites.
    Accepts form data and forwards to n8n for processing.
    """
    submission_id = str(uuid.uuid4())

    try:
        # Normalize the submission
        normalized = _normalize_form_submission(submission, submission_id, request)

        logger.info(f"Form submission received: {submission_id}, phone: {submission.phone}, form: {submission.form_id}")

        # Forward to n8n
        forwarded = await _forward_to_n8n(normalized)

        if not forwarded:
            logger.warning(f"Failed to forward submission {submission_id} to n8n")
            # Still return success to the client - we can retry later

        return {
            "success": True,
            "submission_id": submission_id,
            "message": "Form submitted successfully",
            "forwarded": forwarded
        }

    except Exception as e:
        logger.error(f"Form submission error: {e}")
        return JSONResponse(
            status_code=500,
            content={
                "success": False,
                "error": str(e),
                "submission_id": submission_id
            }
        )


@app.post("/api/quiz", dependencies=[Depends(verify_api_key_optional)])
async def submit_quiz(submission: FormSubmission, request: Request):
    """Submit a quiz form.

    Alias for /api/submit with quiz-specific defaults.
    """
    if not submission.form_id:
        submission.form_id = "quiz"

    return await submit_form(submission, request)


@app.post("/api/callback", dependencies=[Depends(verify_api_key_optional)])
async def submit_callback_request(submission: FormSubmission, request: Request):
    """Submit a callback request form.

    Simple callback form with just phone and optional name.
    """
    if not submission.form_id:
        submission.form_id = "callback"

    return await submit_form(submission, request)


@app.post("/api/contact", dependencies=[Depends(verify_api_key_optional)])
async def submit_contact_form(submission: FormSubmission, request: Request):
    """Submit a contact form.

    Contact form with message.
    """
    if not submission.form_id:
        submission.form_id = "contact"

    return await submit_form(submission, request)


# ========== Webhook for external integrations ==========

@app.post("/webhook/form", dependencies=[Depends(verify_api_key)])
async def form_webhook(request: Request):
    """Receive form data from external systems (Tilda, etc.)

    This endpoint expects raw JSON and tries to extract phone/name/data.
    Requires API key for security.
    """
    submission_id = str(uuid.uuid4())

    try:
        body = await request.json()
        logger.info(f"Form webhook received: {str(body)[:500]}")

        # Try to extract standard fields
        phone = (
            body.get("phone") or
            body.get("Phone") or
            body.get("tel") or
            body.get("telephone") or
            body.get("mobile") or
            ""
        )

        name = (
            body.get("name") or
            body.get("Name") or
            body.get("client_name") or
            body.get("fio") or
            ""
        )

        email = (
            body.get("email") or
            body.get("Email") or
            body.get("e-mail") or
            ""
        )

        if not phone:
            raise HTTPException(status_code=400, detail="Phone number is required")

        # Create submission
        submission = FormSubmission(
            phone=phone,
            name=name if name else None,
            email=email if email else None,
            form_id=body.get("form_id", "webhook"),
            form_name=body.get("form_name"),
            message=body.get("message") or body.get("comment"),
            custom_data=body,
            tenant_id=body.get("tenant_id")
        )

        # Extract UTM
        for utm_field in ["utm_source", "utm_medium", "utm_campaign", "utm_content", "utm_term"]:
            if body.get(utm_field):
                setattr(submission, utm_field, body.get(utm_field))

        normalized = _normalize_form_submission(submission, submission_id, request)
        forwarded = await _forward_to_n8n(normalized)

        return {
            "success": True,
            "submission_id": submission_id,
            "forwarded": forwarded
        }

    except HTTPException:
        raise
    except Exception as e:
        logger.error(f"Webhook error: {e}")
        return JSONResponse(
            status_code=500,
            content={"success": False, "error": str(e)}
        )


# ========== Helper Functions ==========

def _normalize_form_submission(submission: FormSubmission, submission_id: str, request: Request) -> dict:
    """Normalize form submission to standard format for n8n."""

    # Build quiz summary text
    quiz_text = ""
    if submission.quiz_answers:
        quiz_lines = [f"• {a.question}: {a.answer}" for a in submission.quiz_answers]
        quiz_text = "\n".join(quiz_lines)

    # Build full message text
    text_parts = []
    if submission.message:
        text_parts.append(submission.message)
    if quiz_text:
        text_parts.append(f"\n📋 Ответы на квиз:\n{quiz_text}")

    full_text = "\n".join(text_parts) if text_parts else f"Заявка с формы {submission.form_id or 'website'}"

    # Get client IP
    client_ip = request.client.host if request.client else None
    forwarded_for = request.headers.get("x-forwarded-for")
    if forwarded_for:
        client_ip = forwarded_for.split(",")[0].strip()

    # Standard normalized format
    normalized = {
        "channel": "form",
        "external_user_id": submission.phone,  # Use phone as user ID
        "external_chat_id": submission_id,  # Each submission is a "chat"

        "text": full_text,
        "timestamp": datetime.now().isoformat(),

        "client_phone": submission.phone,
        "client_name": submission.name,
        "client_email": submission.email,

        "media": {
            "has_voice": False,
            "voice_url": None,
            "voice_transcribed_text": None,
            "has_images": False,
            "images": [],
            "has_video": False,
            "videos": [],
            "has_document": False
        },

        "meta": {
            "external_message_id": submission_id,
            "ad_channel": "form",
            "form_id": submission.form_id,
            "form_name": submission.form_name,
            "submission_id": submission_id,
            "client_ip": client_ip,
            "user_agent": request.headers.get("user-agent"),
            "tenant_id": submission.tenant_id
        },

        "form_data": {
            "quiz_answers": [a.dict() for a in submission.quiz_answers] if submission.quiz_answers else None,
            "custom_data": submission.custom_data,
            "message": submission.message
        },

        "utm": {
            "source": submission.utm_source,
            "medium": submission.utm_medium,
            "campaign": submission.utm_campaign,
            "content": submission.utm_content,
            "term": submission.utm_term,
            "referrer": submission.referrer,
            "landing_page": submission.landing_page
        },

        "prefilled_data": {
            "model": None,
            "parts_owner": None
        }
    }

    return normalized


async def _forward_to_n8n(normalized_data: dict) -> bool:
    """Forward normalized form data to n8n webhook."""
    if not settings.n8n_webhook_url:
        logger.warning("N8N_WEBHOOK_URL not configured, skipping forward")
        return False

    async with httpx.AsyncClient(timeout=30.0) as client:
        try:
            response = await client.post(
                settings.n8n_webhook_url,
                json=normalized_data
            )
            response.raise_for_status()
            logger.info(f"Form data forwarded to n8n: {response.status_code}")
            return True
        except Exception as e:
            logger.error(f"Failed to forward to n8n: {e}")
            return False


# ========== Run Server ==========

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(
        "app:app",
        host=settings.server_host,
        port=settings.server_port,
        reload=True,
        workers=1
    )
