from typing import Any, Dict, Optional

from neo4j import AsyncGraphDatabase

import config


_driver: Optional[AsyncGraphDatabase] = None


async def get_driver():
    global _driver
    if _driver is None:
        _driver = AsyncGraphDatabase.driver(
            config.NEO4J_URI,
            auth=(config.NEO4J_USER, config.NEO4J_PASSWORD),
        )
    return _driver


async def run_cypher(query: str, params: Dict[str, Any]) -> Any:
    driver = await get_driver()
    async with driver.session() as session:
        result = await session.run(query, params)
        data = await result.data()
        return data


async def ping_neo4j() -> bool:
    try:
        driver = await get_driver()
        async with driver.session() as session:
            await session.run("RETURN 1;")
        return True
    except Exception:
        return False

