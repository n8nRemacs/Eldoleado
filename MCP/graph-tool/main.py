import time
from typing import Any, Dict, Optional

import asyncpg
from fastapi import FastAPI, HTTPException
from pydantic import BaseModel, Field

import config
from db import fetch_query, get_pool, ping_db
from neo4j_client import ping_neo4j, run_cypher


class QueryRequest(BaseModel):
    query_code: str
    params: Dict[str, Any] = Field(default_factory=dict)


class QueryResponse(BaseModel):
    success: bool
    data: Optional[Any] = None
    error: Optional[str] = None
    execution_time_ms: int


app = FastAPI(title="Graph Tool MCP (blind executor)")
db_pool: Optional[asyncpg.Pool] = None


@app.on_event("startup")
async def startup():
    global db_pool
    db_pool = await get_pool()


@app.on_event("shutdown")
async def shutdown():
    global db_pool
    if db_pool:
        await db_pool.close()


@app.get("/health")
async def health():
    db_ok = await ping_db(db_pool)
    neo_ok = await ping_neo4j()
    return {"status": "ok", "postgres": db_ok, "neo4j": neo_ok}


@app.post("/query", response_model=QueryResponse)
async def query(req: QueryRequest):
    start = time.time()
    # fetch cypher by code
    if db_pool is None:
        raise HTTPException(status_code=500, detail="DB pool not ready")

    row = await fetch_query(db_pool, req.query_code)
    if not row:
        return QueryResponse(
            success=False,
            error=f"Query not found: {req.query_code}",
            execution_time_ms=int((time.time() - start) * 1000),
        )

    cypher, param_schema = row

    # simple param pass-through (placeholders $param)
    params = req.params or {}

    try:
        data = await run_cypher(cypher, params)
        return QueryResponse(
            success=True,
            data=data,
            execution_time_ms=int((time.time() - start) * 1000),
        )
    except Exception as exc:  # noqa: BLE001
        return QueryResponse(
            success=False,
            error=str(exc),
            execution_time_ms=int((time.time() - start) * 1000),
        )

