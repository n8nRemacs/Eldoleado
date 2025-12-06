"""VK Community Bot REST API Server (Multi-tenant).

FastAPI server providing HTTP endpoints for VK Community Bot integration.
Supports multiple VK communities with dynamic registration.
Version 2.0.0 - Multi-tenant architecture with Redis + PostgreSQL storage.
"""

import sys
import hashlib
import logging
from typing import Optional, List, Dict, Any
from datetime import datetime
from contextlib import asynccontextmanager

import httpx
from fastapi import FastAPI, Request, HTTPException, Depends, Header, Query
from fastapi.responses import JSONResponse, PlainTextResponse
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field

from config import settings
from vk_client import VKClient, VKAPIError

# Add shared module to path
sys.path.insert(0, str(__file__).replace("\\", "/").rsplit("/", 2)[0])
from shared.storage import (
    init_storage, close_storage, get_credentials_hash,
    save_account, load_accounts, get_account, delete_account
)

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# Channel name for storage
CHANNEL_NAME = "vk"

# Multi-tenant registries
client_cache: Dict[str, VKClient] = {}      # group_hash -> VKClient
account_cache: Dict[str, dict] = {}          # group_hash -> account data (with confirmation_code)


def get_group_hash(group_id: int) -> str:
    """Generate 16-char hash from group_id for webhook URL."""
    return hashlib.sha256(str(group_id).encode()).hexdigest()[:16]


async def get_or_create_client(
    access_token: str,
    group_id: int,
    confirmation_code: Optional[str] = None,
    secret_key: Optional[str] = None
) -> tuple[VKClient, str]:
    """Get existing client or create new one with auto-registration."""
    group_hash = get_group_hash(group_id)

    if group_hash in client_cache:
        return client_cache[group_hash], group_hash

    # Create new client
    client = VKClient(access_token=access_token, group_id=group_id)
    await client.connect()

    # Get confirmation code from VK API if not provided
    actual_confirmation_code = confirmation_code
    if not actual_confirmation_code:
        try:
            actual_confirmation_code = await client.get_callback_confirmation_code()
            logger.info(f"Got confirmation code from VK API for group {group_id}")
        except Exception as e:
            logger.warning(f"Could not get confirmation code from API: {e}")
            actual_confirmation_code = ""

    # Get group info for metadata
    try:
        group_info = await client.get_group_info()
    except Exception as e:
        logger.warning(f"Could not get group info: {e}")
        group_info = {}

    # Save to storage
    credentials = {
        "access_token": access_token,
        "group_id": group_id,
        "confirmation_code": actual_confirmation_code,
        "secret_key": secret_key
    }

    metadata = {
        "group_name": group_info.get("name", ""),
        "screen_name": group_info.get("screen_name", ""),
        "registered_at": datetime.utcnow().isoformat()
    }

    await save_account(CHANNEL_NAME, group_hash, credentials, metadata)

    # Cache
    client_cache[group_hash] = client
    account_cache[group_hash] = {
        "credentials": credentials,
        "metadata": metadata,
        "group_id": group_id,
        "confirmation_code": actual_confirmation_code,
        "secret_key": secret_key
    }

    logger.info(f"Registered VK group {group_id} with hash {group_hash}")
    return client, group_hash


def get_client_by_hash(group_hash: str) -> Optional[VKClient]:
    """Get client by group hash."""
    return client_cache.get(group_hash)


def get_account_by_hash(group_hash: str) -> Optional[dict]:
    """Get account data by group hash."""
    return account_cache.get(group_hash)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Application lifespan - init and cleanup."""
    # Initialize storage
    await init_storage()
    logger.info("Storage initialized")

    # Load existing accounts
    try:
        accounts = await load_accounts(CHANNEL_NAME)
        for acc in accounts:
            try:
                creds = acc.get("credentials", {})
                access_token = creds.get("access_token")
                group_id = creds.get("group_id")
                confirmation_code = creds.get("confirmation_code", "")
                secret_key = creds.get("secret_key")

                if access_token and group_id:
                    group_hash = acc.get("hash") or get_group_hash(group_id)

                    client = VKClient(access_token=access_token, group_id=group_id)
                    await client.connect()

                    client_cache[group_hash] = client
                    account_cache[group_hash] = {
                        "credentials": creds,
                        "metadata": acc.get("metadata", {}),
                        "group_id": group_id,
                        "confirmation_code": confirmation_code,
                        "secret_key": secret_key
                    }

                    logger.info(f"Loaded VK group {group_id} ({group_hash})")
            except Exception as e:
                logger.error(f"Failed to load account: {e}")

        logger.info(f"Loaded {len(client_cache)} VK accounts from storage")
    except Exception as e:
        logger.error(f"Failed to load accounts: {e}")

    yield

    # Cleanup
    for client in client_cache.values():
        try:
            await client.close()
        except Exception as e:
            logger.error(f"Error closing client: {e}")

    await close_storage()
    logger.info("VK multi-tenant server stopped")


app = FastAPI(
    title="VK Community Bot API (Multi-tenant)",
    description="REST API for multiple VK Community Bots with dynamic registration",
    version="2.0.0",
    lifespan=lifespan
)

# CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)


# ========== Pydantic Models ==========

class RegisterAccountRequest(BaseModel):
    access_token: str = Field(..., description="VK Community access token")
    group_id: int = Field(..., description="VK Community/Group ID")
    confirmation_code: Optional[str] = Field(None, description="Callback confirmation code (auto-fetched if not provided)")
    secret_key: Optional[str] = Field(None, description="Webhook secret key")


class SendMessageRequest(BaseModel):
    access_token: Optional[str] = Field(None, description="VK access token (for auto-registration)")
    group_id: Optional[int] = Field(None, description="VK group ID (for auto-registration)")
    peer_id: int = Field(..., description="User ID or chat ID")
    message: str = Field(..., description="Message text")
    attachment: Optional[str] = Field(None, description="Attachment string")
    reply_to: Optional[int] = Field(None, description="Message ID to reply to")
    keyboard: Optional[dict] = Field(None, description="VK keyboard object")


class BulkMessageRequest(BaseModel):
    access_token: Optional[str] = Field(None, description="VK access token")
    group_id: Optional[int] = Field(None, description="VK group ID")
    peer_ids: List[int] = Field(..., description="List of peer IDs")
    message: str = Field(..., description="Message text")


class CallbackServerRequest(BaseModel):
    access_token: Optional[str] = Field(None, description="VK access token")
    group_id: Optional[int] = Field(None, description="VK group ID")
    url: str = Field(..., description="Callback URL")
    title: str = Field(..., description="Server title")
    secret_key: Optional[str] = Field(None, description="Secret key")


# ========== Auth Dependency ==========

async def verify_api_key(x_api_key: Optional[str] = Header(None)):
    """Verify API key if configured."""
    if settings.api_key and settings.api_key != x_api_key:
        raise HTTPException(status_code=401, detail="Invalid API key")
    return True


# ========== Error Handler ==========

@app.exception_handler(VKAPIError)
async def vk_error_handler(request: Request, exc: VKAPIError):
    return JSONResponse(
        status_code=exc.status_code,
        content={
            "success": False,
            "error": exc.message,
            "error_code": exc.error_code
        }
    )


# ========== Health & Info ==========

@app.get("/health")
async def health_check():
    """Health check endpoint."""
    return {
        "status": "healthy",
        "service": "vk-community-api",
        "version": "2.0.0",
        "mode": "multi-tenant",
        "accounts_loaded": len(client_cache),
        "timestamp": datetime.now().isoformat()
    }


@app.get("/info")
async def server_info():
    """Server configuration info."""
    return {
        "service": "vk-community-api",
        "version": "2.0.0",
        "mode": "multi-tenant",
        "accounts_count": len(client_cache),
        "api_version": VKClient.API_VERSION
    }


# ========== Account Management ==========

@app.post("/accounts/register", dependencies=[Depends(verify_api_key)])
async def register_account(request: RegisterAccountRequest):
    """Register a new VK community account."""
    client, group_hash = await get_or_create_client(
        access_token=request.access_token,
        group_id=request.group_id,
        confirmation_code=request.confirmation_code,
        secret_key=request.secret_key
    )

    account = account_cache.get(group_hash, {})

    return {
        "success": True,
        "status": "registered",
        "group_hash": group_hash,
        "group_id": request.group_id,
        "group_name": account.get("metadata", {}).get("group_name", ""),
        "webhook_url": f"/webhook/vk/{group_hash}",
        "confirmation_code": account.get("confirmation_code", "")
    }


@app.get("/accounts", dependencies=[Depends(verify_api_key)])
async def list_accounts():
    """List all registered VK accounts."""
    accounts = []
    for group_hash, account in account_cache.items():
        accounts.append({
            "group_hash": group_hash,
            "group_id": account.get("group_id"),
            "group_name": account.get("metadata", {}).get("group_name", ""),
            "screen_name": account.get("metadata", {}).get("screen_name", ""),
            "webhook_url": f"/webhook/vk/{group_hash}",
            "registered_at": account.get("metadata", {}).get("registered_at")
        })

    return {
        "success": True,
        "count": len(accounts),
        "accounts": accounts
    }


@app.get("/accounts/{group_hash}", dependencies=[Depends(verify_api_key)])
async def get_account_info(group_hash: str):
    """Get account info by hash."""
    account = get_account_by_hash(group_hash)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    return {
        "success": True,
        "group_hash": group_hash,
        "group_id": account.get("group_id"),
        "group_name": account.get("metadata", {}).get("group_name", ""),
        "screen_name": account.get("metadata", {}).get("screen_name", ""),
        "webhook_url": f"/webhook/vk/{group_hash}",
        "confirmation_code": account.get("confirmation_code", ""),
        "registered_at": account.get("metadata", {}).get("registered_at")
    }


@app.get("/accounts/{group_hash}/confirmation-code", dependencies=[Depends(verify_api_key)])
async def get_confirmation_code_endpoint(group_hash: str):
    """Get VK Callback API confirmation code for specific account."""
    account = get_account_by_hash(group_hash)
    if not account:
        raise HTTPException(status_code=404, detail="Account not found")

    return {
        "success": True,
        "group_hash": group_hash,
        "group_id": account.get("group_id"),
        "confirmation_code": account.get("confirmation_code", "")
    }


@app.delete("/accounts/{group_hash}", dependencies=[Depends(verify_api_key)])
async def unregister_account(group_hash: str):
    """Unregister a VK account."""
    if group_hash not in client_cache:
        raise HTTPException(status_code=404, detail="Account not found")

    # Close client
    client = client_cache.pop(group_hash)
    account = account_cache.pop(group_hash, {})
    await client.close()

    # Remove from storage
    await delete_account(CHANNEL_NAME, group_hash)

    return {
        "success": True,
        "status": "unregistered",
        "group_hash": group_hash,
        "group_id": account.get("group_id")
    }


# ========== Group Info ==========

@app.get("/api/group", dependencies=[Depends(verify_api_key)])
async def get_group(
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None),
    group_hash: Optional[str] = Query(None)
):
    """Get community info."""
    # Determine which client to use
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    info = await client.get_group_info()
    return {
        "success": True,
        "group": info
    }


# ========== Message Endpoints ==========

@app.post("/api/messages/send", dependencies=[Depends(verify_api_key)])
async def send_message(request: SendMessageRequest, group_hash: Optional[str] = Query(None)):
    """Send message to user/chat."""
    # Determine which client to use
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.group_id:
        client, _ = await get_or_create_client(request.access_token, request.group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    message_id = await client.send_message(
        peer_id=request.peer_id,
        message=request.message,
        attachment=request.attachment,
        reply_to=request.reply_to,
        keyboard=request.keyboard
    )
    return {
        "success": True,
        "peer_id": request.peer_id,
        "message_id": message_id,
        "message": "Message sent"
    }


@app.post("/api/messages/send/bulk", dependencies=[Depends(verify_api_key)])
async def send_bulk_messages(request: BulkMessageRequest, group_hash: Optional[str] = Query(None)):
    """Send message to multiple users."""
    # Determine which client to use
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.group_id:
        client, _ = await get_or_create_client(request.access_token, request.group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    results = []
    for peer_id in request.peer_ids:
        try:
            msg_id = await client.send_message(
                peer_id=peer_id,
                message=request.message
            )
            results.append({"peer_id": peer_id, "success": True, "message_id": msg_id})
        except VKAPIError as e:
            results.append({"peer_id": peer_id, "success": False, "error": e.message})
        except Exception as e:
            results.append({"peer_id": peer_id, "success": False, "error": str(e)})

    success_count = sum(1 for r in results if r["success"])
    return {
        "success": True,
        "total": len(results),
        "sent": success_count,
        "failed": len(results) - success_count,
        "results": results
    }


@app.put("/api/messages/{message_id}", dependencies=[Depends(verify_api_key)])
async def edit_message(
    message_id: int,
    peer_id: int = Query(...),
    message: str = Query(...),
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Edit sent message."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.edit_message(
        peer_id=peer_id,
        message_id=message_id,
        message=message
    )
    return {
        "success": True,
        "message_id": message_id,
        "message": "Message edited"
    }


@app.delete("/api/messages", dependencies=[Depends(verify_api_key)])
async def delete_messages(
    message_ids: str = Query(..., description="Comma-separated message IDs"),
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Delete messages."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    ids = [int(x.strip()) for x in message_ids.split(",")]
    result = await client.delete_message(ids)
    return {
        "success": True,
        "deleted": ids,
        "result": result
    }


# ========== Conversation Endpoints ==========

@app.get("/api/conversations", dependencies=[Depends(verify_api_key)])
async def get_conversations(
    count: int = Query(20, ge=1, le=200),
    offset: int = Query(0, ge=0),
    filter: str = Query("all", regex="^(all|unread|important|unanswered)$"),
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Get conversations list."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.get_conversations(
        count=count,
        offset=offset,
        filter=filter
    )
    return {
        "success": True,
        "data": result
    }


@app.get("/api/conversations/{peer_id}/history", dependencies=[Depends(verify_api_key)])
async def get_history(
    peer_id: int,
    count: int = Query(20, ge=1, le=200),
    offset: int = Query(0, ge=0),
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Get message history from conversation."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.get_history(
        peer_id=peer_id,
        count=count,
        offset=offset
    )
    return {
        "success": True,
        "peer_id": peer_id,
        "data": result
    }


@app.post("/api/conversations/{peer_id}/read", dependencies=[Depends(verify_api_key)])
async def mark_as_read(
    peer_id: int,
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Mark conversation as read."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.mark_as_read(peer_id)
    return {
        "success": True,
        "peer_id": peer_id,
        "message": "Marked as read"
    }


# ========== User Endpoints ==========

@app.get("/api/users/{user_ids}", dependencies=[Depends(verify_api_key)])
async def get_users(
    user_ids: str,
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Get user info by IDs (comma-separated)."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    ids = [int(x.strip()) for x in user_ids.split(",")]
    users = await client.get_users(ids)
    return {
        "success": True,
        "users": users
    }


# ========== Callback Server Management ==========

@app.get("/api/callback/servers", dependencies=[Depends(verify_api_key)])
async def get_callback_servers(
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Get callback servers list."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.get_callback_servers()
    return {
        "success": True,
        "servers": result
    }


@app.post("/api/callback/servers", dependencies=[Depends(verify_api_key)])
async def add_callback_server(request: CallbackServerRequest, group_hash: Optional[str] = Query(None)):
    """Add callback server."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif request.access_token and request.group_id:
        client, _ = await get_or_create_client(request.access_token, request.group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    result = await client.add_callback_server(
        url=request.url,
        title=request.title,
        secret_key=request.secret_key
    )
    return {
        "success": True,
        "server_id": result.get("server_id"),
        "message": "Callback server added"
    }


@app.delete("/api/callback/servers/{server_id}", dependencies=[Depends(verify_api_key)])
async def delete_callback_server(
    server_id: int,
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Delete callback server."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    await client.delete_callback_server(server_id)
    return {
        "success": True,
        "message": "Callback server deleted"
    }


@app.put("/api/callback/servers/{server_id}/settings", dependencies=[Depends(verify_api_key)])
async def set_callback_settings(
    server_id: int,
    message_new: bool = True,
    message_reply: bool = False,
    message_allow: bool = True,
    message_deny: bool = True,
    group_hash: Optional[str] = Query(None),
    access_token: Optional[str] = Query(None),
    group_id: Optional[int] = Query(None)
):
    """Set callback events for server."""
    if group_hash:
        client = get_client_by_hash(group_hash)
        if not client:
            raise HTTPException(status_code=404, detail="Account not found")
    elif access_token and group_id:
        client, _ = await get_or_create_client(access_token, group_id)
    else:
        raise HTTPException(status_code=400, detail="Provide group_hash or (access_token + group_id)")

    await client.set_callback_settings(
        server_id=server_id,
        message_new=message_new,
        message_reply=message_reply,
        message_allow=message_allow,
        message_deny=message_deny
    )
    return {
        "success": True,
        "message": "Settings updated"
    }


# ========== VK Callback API Handler (Multi-tenant) ==========

@app.post("/webhook/vk/{group_hash}")
async def vk_callback_multitenant(group_hash: str, request: Request):
    """Handle VK Callback API events for specific account.

    Each registered VK group has its own webhook URL with unique confirmation code.
    """
    # Get account by hash
    account = get_account_by_hash(group_hash)
    if not account:
        logger.warning(f"Webhook for unknown group_hash: {group_hash}")
        raise HTTPException(status_code=404, detail="Account not found")

    client = get_client_by_hash(group_hash)

    try:
        body = await request.json()
        event_type = body.get("type")
        incoming_group_id = body.get("group_id")

        logger.info(f"VK callback [{group_hash}]: type={event_type}, group={incoming_group_id}")

        # Confirmation request - return per-account confirmation code
        if event_type == "confirmation":
            expected_group_id = account.get("group_id")
            if incoming_group_id == expected_group_id:
                confirmation_code = account.get("confirmation_code", "")
                logger.info(f"Returning confirmation code for group {expected_group_id}")
                return PlainTextResponse(confirmation_code)
            else:
                logger.warning(f"Confirmation for wrong group: expected {expected_group_id}, got {incoming_group_id}")
                return PlainTextResponse("bad group_id", status_code=400)

        # Verify secret if configured
        secret_key = account.get("secret_key")
        if secret_key:
            if body.get("secret") != secret_key:
                logger.warning(f"Bad secret key for group_hash {group_hash}")
                return PlainTextResponse("bad secret", status_code=403)

        # Process events
        if event_type == "message_new":
            await _handle_message_new(body, account)
        elif event_type == "message_reply":
            logger.debug("message_reply event (sent by community)")
        elif event_type == "message_allow":
            user_id = body.get("object", {}).get("user_id")
            logger.info(f"User {user_id} allowed messages")
        elif event_type == "message_deny":
            user_id = body.get("object", {}).get("user_id")
            logger.info(f"User {user_id} denied messages")
        elif event_type == "message_event":
            await _handle_message_event(body, account)

        # Always return "ok" to VK
        return PlainTextResponse("ok")

    except Exception as e:
        logger.error(f"VK callback error [{group_hash}]: {e}")
        # Still return ok to avoid retries
        return PlainTextResponse("ok")


@app.post("/webhook/vk")
async def vk_callback_legacy(request: Request):
    """Legacy webhook handler for backward compatibility.

    Tries to match incoming group_id to registered accounts.
    """
    try:
        body = await request.json()
        event_type = body.get("type")
        incoming_group_id = body.get("group_id")

        logger.info(f"VK legacy callback: type={event_type}, group={incoming_group_id}")

        # Find account by group_id
        matched_hash = None
        matched_account = None
        for gh, acc in account_cache.items():
            if acc.get("group_id") == incoming_group_id:
                matched_hash = gh
                matched_account = acc
                break

        if not matched_account:
            logger.warning(f"Legacy webhook: no account for group_id {incoming_group_id}")
            # Still handle confirmation if we have any accounts
            if event_type == "confirmation" and account_cache:
                # Return first account's code (backward compat)
                first_hash = next(iter(account_cache))
                first_acc = account_cache[first_hash]
                if first_acc.get("group_id") == incoming_group_id:
                    return PlainTextResponse(first_acc.get("confirmation_code", ""))
            return PlainTextResponse("ok")

        # Confirmation request
        if event_type == "confirmation":
            return PlainTextResponse(matched_account.get("confirmation_code", ""))

        # Verify secret if configured
        secret_key = matched_account.get("secret_key")
        if secret_key:
            if body.get("secret") != secret_key:
                logger.warning("Bad secret key in legacy webhook")
                return PlainTextResponse("bad secret", status_code=403)

        # Process events
        if event_type == "message_new":
            await _handle_message_new(body, matched_account)
        elif event_type == "message_event":
            await _handle_message_event(body, matched_account)

        return PlainTextResponse("ok")

    except Exception as e:
        logger.error(f"VK legacy callback error: {e}")
        return PlainTextResponse("ok")


async def _handle_message_new(body: dict, account: dict):
    """Handle new message event."""
    obj = body.get("object", {})
    message = obj.get("message", {})

    from_id = message.get("from_id", 0)
    peer_id = message.get("peer_id", 0)
    text = message.get("text", "")
    date = message.get("date", 0)
    message_id = message.get("id", 0)
    attachments = message.get("attachments", [])

    # Skip messages from community
    if from_id < 0:
        logger.debug("Skipping message from community")
        return

    # Normalize and forward
    normalized = _normalize_vk_message(message, body, account)
    await _forward_to_n8n(normalized)


async def _handle_message_event(body: dict, account: dict):
    """Handle callback button event."""
    obj = body.get("object", {})
    logger.info(f"Message event: {obj}")


def _normalize_vk_message(message: dict, callback_body: dict, account: dict) -> dict:
    """Normalize VK message to standard format with tenant info."""
    from_id = message.get("from_id", 0)
    peer_id = message.get("peer_id", 0)
    text = message.get("text", "")
    date = message.get("date", 0)
    message_id = message.get("id", 0)
    attachments = message.get("attachments", [])

    # Detect media types
    has_photo = any(a.get("type") == "photo" for a in attachments)
    has_voice = any(a.get("type") == "audio_message" for a in attachments)
    has_video = any(a.get("type") == "video" for a in attachments)
    has_doc = any(a.get("type") == "doc" for a in attachments)

    # Extract media URLs
    images = []
    voice_url = None
    videos = []

    for att in attachments:
        att_type = att.get("type")
        if att_type == "photo":
            photo = att.get("photo", {})
            sizes = photo.get("sizes", [])
            if sizes:
                largest = max(sizes, key=lambda s: s.get("width", 0) * s.get("height", 0))
                images.append({"url": largest.get("url")})
        elif att_type == "audio_message":
            audio = att.get("audio_message", {})
            voice_url = audio.get("link_mp3") or audio.get("link_ogg")
        elif att_type == "video":
            video = att.get("video", {})
            videos.append({
                "url": f"https://vk.com/video{video.get('owner_id')}_{video.get('id')}"
            })

    # Get group_id from account for tenant resolution
    group_id = account.get("group_id") or callback_body.get("group_id")

    # Standard normalized format with tenant info
    normalized = {
        "channel": "vk",
        "external_user_id": str(from_id),
        "external_chat_id": str(peer_id),

        # Tenant identifier for n8n resolution
        "group_id": group_id,

        "text": text,
        "timestamp": datetime.fromtimestamp(date).isoformat() if date else datetime.now().isoformat(),

        "client_phone": None,
        "client_name": None,
        "client_email": None,

        "media": {
            "has_voice": has_voice,
            "voice_url": voice_url,
            "voice_transcribed_text": None,
            "has_images": has_photo,
            "images": images,
            "has_video": has_video,
            "videos": videos,
            "has_document": has_doc
        },

        "meta": {
            "external_message_id": str(message_id),
            "ad_channel": "vk",
            "ad_id": None,
            "group_id": group_id,
            "raw": message
        },

        "prefilled_data": {
            "model": None,
            "parts_owner": None
        }
    }

    return normalized


async def _forward_to_n8n(normalized_data: dict) -> bool:
    """Forward normalized message to n8n webhook."""
    if not settings.n8n_webhook_url:
        logger.warning("N8N_WEBHOOK_URL not configured")
        return False

    async with httpx.AsyncClient(timeout=30.0) as client:
        try:
            response = await client.post(
                settings.n8n_webhook_url,
                json=normalized_data
            )
            response.raise_for_status()
            logger.info(f"Forwarded to n8n: {response.status_code}")
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
