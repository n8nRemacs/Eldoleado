"""
Tunnel Server - Main entry point

Starts:
- FastAPI server with WebSocket support
- Pipeline orchestrator
- Background tasks (health checks, etc.)
"""

import asyncio
import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI, WebSocket, WebSocketDisconnect, Query
from fastapi.middleware.cors import CORSMiddleware
import uvicorn

from app.config import settings
from app.input import api_router, webhook_router, manager
from app.input.webhooks import set_webhook_handler
from app.input.api import set_orchestrator
from app.pipeline import PipelineOrchestrator

# Configure logging
logging.basicConfig(
    level=getattr(logging, settings.log_level),
    format="%(asctime)s [%(levelname)s] %(name)s: %(message)s"
)
logger = logging.getLogger("TunnelServer")


# Global orchestrator
orchestrator: PipelineOrchestrator = None


# === Lifespan ===

@asynccontextmanager
async def lifespan(app: FastAPI):
    """Startup and shutdown"""
    global orchestrator

    logger.info("Starting Tunnel Server...")

    # Initialize database pools
    pg_pool = None
    neo4j_driver = None

    # TODO: Initialize real database connections
    # pg_pool = await asyncpg.create_pool(settings.postgres.dsn)
    # neo4j_driver = neo4j.AsyncGraphDatabase.driver(...)

    # Initialize orchestrator
    orchestrator = PipelineOrchestrator(pg_pool, neo4j_driver)

    # Set up handlers
    manager.set_incoming_handler(handle_incoming_message)
    set_webhook_handler(handle_webhook)
    set_orchestrator(orchestrator)  # For API routes

    # Start background tasks
    health_task = asyncio.create_task(health_check_loop())

    logger.info(f"Tunnel Server started on port {settings.port}")

    yield

    # Shutdown
    logger.info("Shutting down...")
    health_task.cancel()

    # Close database connections
    # if pg_pool:
    #     await pg_pool.close()
    # if neo4j_driver:
    #     await neo4j_driver.close()


# === FastAPI App ===

app = FastAPI(
    title="Tunnel Server",
    description="WebSocket tunnel server for omnichannel messaging",
    version="1.0.0",
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

# Include routers
app.include_router(api_router)
app.include_router(webhook_router)


# === WebSocket Endpoint ===

@app.websocket("/ws")
async def websocket_endpoint(
    websocket: WebSocket,
    server_id: str = Query(...),
    secret: str = Query(...)
):
    """
    WebSocket tunnel endpoint.

    Phones connect here to establish tunnel.
    """
    # TODO: Validate secret against database

    conn = await manager.connect(websocket, server_id, secret)

    try:
        while True:
            data = await websocket.receive_json()
            await manager.handle_message(server_id, data)

    except WebSocketDisconnect:
        manager.disconnect(server_id)
    except Exception as e:
        logger.error(f"WebSocket error for {server_id}: {e}")
        manager.disconnect(server_id)


# === Handlers ===

async def handle_incoming_message(server_id: str, data: dict):
    """Handle incoming message from tunnel"""
    if not orchestrator:
        logger.error("Orchestrator not initialized")
        return

    try:
        service = data.get("service", "unknown")
        message_data = data.get("data", {})

        result = await orchestrator.process_incoming(
            server_id=server_id,
            channel=service,
            data=message_data
        )

        logger.info(f"Processed incoming from {service}: {result.message.id}")

    except Exception as e:
        logger.error(f"Error processing incoming message: {e}")


async def handle_webhook(source: str, data: dict):
    """Handle webhook from external source"""
    if not orchestrator:
        logger.error("Orchestrator not initialized")
        return

    try:
        result = await orchestrator.process_webhook(source, data)
        if result:
            logger.info(f"Processed webhook from {source}")

    except Exception as e:
        logger.error(f"Error processing webhook: {e}")


# === Background Tasks ===

async def health_check_loop():
    """Periodic health check for tunnels"""
    while True:
        try:
            await asyncio.sleep(60)  # Every minute
            await manager.ping_all()
        except asyncio.CancelledError:
            break
        except Exception as e:
            logger.error(f"Health check error: {e}")


# === Main ===

if __name__ == "__main__":
    uvicorn.run(
        "main:app",
        host=settings.host,
        port=settings.port,
        reload=False,
        log_level=settings.log_level.lower()
    )
