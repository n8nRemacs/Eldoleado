#!/usr/bin/env python3
"""
MCP SSH Server - Execute commands on remote servers
"""
import asyncio
import json
import os
import sys
from pathlib import Path
from typing import Any

# MCP SDK
from mcp.server import Server
from mcp.server.stdio import stdio_server
from mcp.types import Tool, TextContent

import asyncssh

# Config file path
CONFIG_PATH = Path(__file__).parent / "servers.json"

# Server instance
server = Server("mcp-ssh")

def load_config() -> dict:
    """Load server configurations"""
    if CONFIG_PATH.exists():
        with open(CONFIG_PATH, "r", encoding="utf-8") as f:
            return json.load(f)
    return {"servers": {}}

def save_config(config: dict):
    """Save server configurations"""
    with open(CONFIG_PATH, "w", encoding="utf-8") as f:
        json.dump(config, f, indent=2, ensure_ascii=False)

@server.list_tools()
async def list_tools() -> list[Tool]:
    """List available SSH tools"""
    return [
        Tool(
            name="ssh_exec",
            description="Execute command on remote server via SSH",
            inputSchema={
                "type": "object",
                "properties": {
                    "server": {
                        "type": "string",
                        "description": "Server alias (e.g., 'ru', 'fi', 'new') or IP address"
                    },
                    "command": {
                        "type": "string",
                        "description": "Command to execute"
                    },
                    "timeout": {
                        "type": "integer",
                        "description": "Timeout in seconds (default: 30)",
                        "default": 30
                    }
                },
                "required": ["server", "command"]
            }
        ),
        Tool(
            name="ssh_add_server",
            description="Add or update SSH server configuration",
            inputSchema={
                "type": "object",
                "properties": {
                    "alias": {
                        "type": "string",
                        "description": "Server alias (e.g., 'ru', 'fi', 'new')"
                    },
                    "host": {
                        "type": "string",
                        "description": "Server IP or hostname"
                    },
                    "user": {
                        "type": "string",
                        "description": "SSH username",
                        "default": "root"
                    },
                    "password": {
                        "type": "string",
                        "description": "SSH password (optional if using key)"
                    },
                    "key_path": {
                        "type": "string",
                        "description": "Path to SSH private key (optional)"
                    },
                    "port": {
                        "type": "integer",
                        "description": "SSH port",
                        "default": 22
                    }
                },
                "required": ["alias", "host"]
            }
        ),
        Tool(
            name="ssh_list_servers",
            description="List configured SSH servers",
            inputSchema={
                "type": "object",
                "properties": {}
            }
        ),
        Tool(
            name="ssh_remove_server",
            description="Remove SSH server from configuration",
            inputSchema={
                "type": "object",
                "properties": {
                    "alias": {
                        "type": "string",
                        "description": "Server alias to remove"
                    }
                },
                "required": ["alias"]
            }
        )
    ]

@server.call_tool()
async def call_tool(name: str, arguments: dict[str, Any]) -> list[TextContent]:
    """Handle tool calls"""

    if name == "ssh_exec":
        return await ssh_exec(arguments)
    elif name == "ssh_add_server":
        return await ssh_add_server(arguments)
    elif name == "ssh_list_servers":
        return await ssh_list_servers(arguments)
    elif name == "ssh_remove_server":
        return await ssh_remove_server(arguments)
    else:
        return [TextContent(type="text", text=f"Unknown tool: {name}")]

async def ssh_exec(args: dict) -> list[TextContent]:
    """Execute SSH command"""
    server_ref = args.get("server", "")
    command = args.get("command", "")
    timeout = args.get("timeout", 30)

    if not command:
        return [TextContent(type="text", text="Error: command is required")]

    config = load_config()
    servers = config.get("servers", {})

    # Find server config
    if server_ref in servers:
        srv = servers[server_ref]
    else:
        # Try as direct IP
        srv = {
            "host": server_ref,
            "user": "root",
            "port": 22
        }

    host = srv.get("host", server_ref)
    user = srv.get("user", "root")
    port = srv.get("port", 22)
    password = srv.get("password")
    key_path = srv.get("key_path")

    try:
        # Build connection options
        conn_opts = {
            "host": host,
            "port": port,
            "username": user,
            "known_hosts": None,  # Disable host key checking
        }

        if password:
            conn_opts["password"] = password
        if key_path and os.path.exists(key_path):
            conn_opts["client_keys"] = [key_path]

        # Connect and execute
        async with asyncssh.connect(**conn_opts) as conn:
            result = await asyncio.wait_for(
                conn.run(command, check=False),
                timeout=timeout
            )

            output = ""
            if result.stdout:
                output += result.stdout
            if result.stderr:
                output += f"\n[STDERR]\n{result.stderr}"
            if result.exit_status != 0:
                output += f"\n[EXIT CODE: {result.exit_status}]"

            return [TextContent(type="text", text=output.strip() or "(empty output)")]

    except asyncio.TimeoutError:
        return [TextContent(type="text", text=f"Error: Command timed out after {timeout}s")]
    except asyncssh.Error as e:
        return [TextContent(type="text", text=f"SSH Error: {e}")]
    except Exception as e:
        return [TextContent(type="text", text=f"Error: {e}")]

async def ssh_add_server(args: dict) -> list[TextContent]:
    """Add server to config"""
    alias = args.get("alias", "")
    host = args.get("host", "")

    if not alias or not host:
        return [TextContent(type="text", text="Error: alias and host are required")]

    config = load_config()
    if "servers" not in config:
        config["servers"] = {}

    config["servers"][alias] = {
        "host": host,
        "user": args.get("user", "root"),
        "port": args.get("port", 22),
    }

    if args.get("password"):
        config["servers"][alias]["password"] = args["password"]
    if args.get("key_path"):
        config["servers"][alias]["key_path"] = args["key_path"]

    save_config(config)

    return [TextContent(type="text", text=f"Server '{alias}' added: {host}")]

async def ssh_list_servers(args: dict) -> list[TextContent]:
    """List configured servers"""
    config = load_config()
    servers = config.get("servers", {})

    if not servers:
        return [TextContent(type="text", text="No servers configured")]

    lines = ["Configured servers:"]
    for alias, srv in servers.items():
        auth = "password" if srv.get("password") else "key" if srv.get("key_path") else "none"
        lines.append(f"  {alias}: {srv.get('user', 'root')}@{srv.get('host')}:{srv.get('port', 22)} ({auth})")

    return [TextContent(type="text", text="\n".join(lines))]

async def ssh_remove_server(args: dict) -> list[TextContent]:
    """Remove server from config"""
    alias = args.get("alias", "")

    config = load_config()
    if alias in config.get("servers", {}):
        del config["servers"][alias]
        save_config(config)
        return [TextContent(type="text", text=f"Server '{alias}' removed")]
    else:
        return [TextContent(type="text", text=f"Server '{alias}' not found")]

async def main():
    """Main entry point"""
    async with stdio_server() as (read_stream, write_stream):
        await server.run(read_stream, write_stream, server.create_initialization_options())

if __name__ == "__main__":
    asyncio.run(main())
