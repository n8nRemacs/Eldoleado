"""
HTTP Proxy Channel - Generic HTTP proxy
Port: 3010

Uses mobile network as proxy for web scraping.
"""

import logging
import os
from typing import Optional, Dict, List

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx
from dotenv import load_dotenv

load_dotenv()

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger("HTTPProxy")

# Config
PORT = int(os.getenv("PORT", 3010))
HOST = os.getenv("HOST", "0.0.0.0")
MAX_TIMEOUT = int(os.getenv("MAX_TIMEOUT", 60))
MAX_RESPONSE_SIZE = int(os.getenv("MAX_RESPONSE_SIZE", 10 * 1024 * 1024))


# === Pydantic Models ===

class FetchRequest(BaseModel):
    url: str
    method: str = "GET"
    headers: Optional[Dict[str, str]] = None
    body: Optional[str] = None
    timeout: int = 30
    follow_redirects: bool = True


class FetchResponse(BaseModel):
    status: int
    headers: Dict[str, str]
    body: str
    url: str


class BatchRequest(BaseModel):
    urls: List[str]
    timeout: int = 30


# === FastAPI ===

app = FastAPI(title="HTTP Proxy Channel", version="1.0.0")


@app.get("/health")
async def health():
    return {"status": "ok"}


@app.post("/fetch", response_model=FetchResponse)
async def fetch(req: FetchRequest):
    """Fetch URL through mobile network"""
    if req.timeout > MAX_TIMEOUT:
        req.timeout = MAX_TIMEOUT

    try:
        async with httpx.AsyncClient(
            follow_redirects=req.follow_redirects,
            timeout=req.timeout
        ) as client:
            response = await client.request(
                method=req.method,
                url=req.url,
                headers=req.headers,
                content=req.body
            )

            body = response.text
            if len(body) > MAX_RESPONSE_SIZE:
                body = body[:MAX_RESPONSE_SIZE]

            return FetchResponse(
                status=response.status_code,
                headers=dict(response.headers),
                body=body,
                url=str(response.url)
            )

    except httpx.TimeoutException:
        raise HTTPException(504, "Request timeout")
    except httpx.RequestError as e:
        raise HTTPException(502, f"Request error: {str(e)}")
    except Exception as e:
        logger.error(f"Fetch error: {e}")
        raise HTTPException(500, str(e))


@app.post("/batch")
async def batch_fetch(req: BatchRequest):
    """Fetch multiple URLs in parallel"""
    if len(req.urls) > 10:
        raise HTTPException(400, "Max 10 URLs per batch")

    results = []

    async with httpx.AsyncClient(timeout=req.timeout) as client:
        for url in req.urls:
            try:
                response = await client.get(url)
                results.append({
                    "url": url,
                    "status": response.status_code,
                    "body": response.text[:50000]
                })
            except Exception as e:
                results.append({
                    "url": url,
                    "status": 0,
                    "error": str(e)
                })

    return results


@app.post("/render")
async def render(url: str, wait_selector: str = None, timeout: int = 60):
    """Fetch with JS rendering (requires playwright)"""
    try:
        from playwright.async_api import async_playwright
    except ImportError:
        raise HTTPException(501, "Playwright not installed")

    if timeout > MAX_TIMEOUT:
        timeout = MAX_TIMEOUT

    try:
        async with async_playwright() as p:
            browser = await p.chromium.launch(headless=True)
            page = await browser.new_page()

            await page.goto(url, timeout=timeout * 1000)

            if wait_selector:
                await page.wait_for_selector(wait_selector, timeout=timeout * 1000)
            else:
                await page.wait_for_timeout(2000)

            html = await page.content()
            final_url = page.url

            await browser.close()

            if len(html) > MAX_RESPONSE_SIZE:
                html = html[:MAX_RESPONSE_SIZE]

            return {
                "status": 200,
                "body": html,
                "url": final_url
            }

    except Exception as e:
        logger.error(f"Render error: {e}")
        raise HTTPException(500, str(e))


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=HOST, port=PORT)
