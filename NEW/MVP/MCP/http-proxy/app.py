"""
HTTP Proxy Service
Port: 3010

Использует мобильный интернет телефона как прокси для HTTP запросов.
Use cases: парсинг сайтов, проверка цен, обход блокировок.
"""

import logging
from typing import Optional, Dict, Any

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx

import config

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)


# === Pydantic models ===

class FetchRequest(BaseModel):
    url: str
    method: str = "GET"
    headers: Optional[Dict[str, str]] = None
    body: Optional[str] = None
    timeout: int = 30
    follow_redirects: bool = True


class RenderRequest(BaseModel):
    url: str
    wait_selector: Optional[str] = None
    wait_time: int = 2  # seconds
    timeout: int = 60


class FetchResponse(BaseModel):
    status: int
    headers: Dict[str, str]
    body: str
    url: str  # final URL after redirects


# === FastAPI app ===

app = FastAPI(
    title="HTTP Proxy Service",
    description="Mobile proxy for web scraping",
    version="1.0.0"
)


@app.get("/health")
async def health():
    """Health check"""
    return {
        "status": "ok",
        "browser_enabled": config.BROWSER_ENABLED
    }


@app.post("/fetch", response_model=FetchResponse)
async def fetch(request: FetchRequest):
    """
    Fetch URL through mobile network.

    Simple HTTP request without JS rendering.
    """
    if request.timeout > config.MAX_TIMEOUT:
        request.timeout = config.MAX_TIMEOUT

    try:
        async with httpx.AsyncClient(
            follow_redirects=request.follow_redirects,
            timeout=request.timeout
        ) as client:
            response = await client.request(
                method=request.method,
                url=request.url,
                headers=request.headers,
                content=request.body
            )

            # Limit response size
            body = response.text
            if len(body) > config.MAX_RESPONSE_SIZE:
                body = body[:config.MAX_RESPONSE_SIZE]
                logger.warning(f"Response truncated: {request.url}")

            return FetchResponse(
                status=response.status_code,
                headers=dict(response.headers),
                body=body,
                url=str(response.url)
            )

    except httpx.TimeoutException:
        raise HTTPException(status_code=504, detail="Request timeout")
    except httpx.RequestError as e:
        raise HTTPException(status_code=502, detail=f"Request error: {str(e)}")
    except Exception as e:
        logger.error(f"Fetch error: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/render")
async def render(request: RenderRequest):
    """
    Fetch URL with JS rendering (headless browser).

    Requires BROWSER_ENABLED=true and playwright installed.
    """
    if not config.BROWSER_ENABLED:
        raise HTTPException(
            status_code=501,
            detail="Browser rendering not enabled. Set BROWSER_ENABLED=true"
        )

    try:
        from playwright.async_api import async_playwright
    except ImportError:
        raise HTTPException(
            status_code=501,
            detail="Playwright not installed. Run: pip install playwright && playwright install chromium"
        )

    if request.timeout > config.MAX_TIMEOUT:
        request.timeout = config.MAX_TIMEOUT

    try:
        async with async_playwright() as p:
            browser = await p.chromium.launch(headless=True)
            page = await browser.new_page()

            await page.goto(request.url, timeout=request.timeout * 1000)

            # Wait for selector or time
            if request.wait_selector:
                await page.wait_for_selector(
                    request.wait_selector,
                    timeout=request.timeout * 1000
                )
            else:
                await page.wait_for_timeout(request.wait_time * 1000)

            html = await page.content()
            final_url = page.url

            await browser.close()

            # Limit response size
            if len(html) > config.MAX_RESPONSE_SIZE:
                html = html[:config.MAX_RESPONSE_SIZE]

            return {
                "status": 200,
                "body": html,
                "url": final_url
            }

    except Exception as e:
        logger.error(f"Render error: {e}")
        raise HTTPException(status_code=500, detail=str(e))


@app.post("/batch")
async def batch_fetch(urls: list[str], timeout: int = 30):
    """
    Fetch multiple URLs in parallel.
    """
    if len(urls) > 10:
        raise HTTPException(status_code=400, detail="Max 10 URLs per batch")

    async with httpx.AsyncClient(timeout=timeout) as client:
        results = []
        for url in urls:
            try:
                response = await client.get(url)
                results.append({
                    "url": url,
                    "status": response.status_code,
                    "body": response.text[:50000]  # limit per item
                })
            except Exception as e:
                results.append({
                    "url": url,
                    "status": 0,
                    "error": str(e)
                })

        return results


if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host=config.HOST, port=config.PORT)
