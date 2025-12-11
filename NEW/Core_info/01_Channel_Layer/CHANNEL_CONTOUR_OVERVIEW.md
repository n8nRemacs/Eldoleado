# Channel Contour — Overview

> MCP adapters + Response Builder for receiving and sending messages across all channels

---

## Purpose

Channel Contour is responsible for **both IN and OUT**:
- **IN:** Receive messages from messengers, normalize, transcribe voice
- **OUT:** Format responses for channel (Response Builder), send via MCP

**Principle:** All channel-specific logic stays here. Core works with unified format.

---

## Architecture

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           CHANNEL CONTOUR                                    │
│                         (IN + OUT unified)                                   │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ══════════════════════════════ IN FLOW ═══════════════════════════════     │
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  MCP ADAPTERS (receive from messengers)                              │    │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐       │    │
│  │  │Telegram │ │WhatsApp │ │  Avito  │ │   VK    │ │   MAX   │       │    │
│  │  └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘ └────┬────┘       │    │
│  │       └───────────┴─────┬─────┴───────────┴───────────┘             │    │
│  └─────────────────────────│───────────────────────────────────────────┘    │
│                            ↓                                                 │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  n8n IN Workflows (ELO_In_*)                                         │    │
│  │  • Receive from MCP adapter                                          │    │
│  │  • Voice transcription (OpenAI Whisper)                              │    │
│  │  • Normalize to ELO Core Contract                                    │    │
│  │  • Add message_id, trace_id                                          │    │
│  │  • → Send to MCP-INPUT /ingest                                       │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                              │
│  ══════════════════════════════ OUT FLOW ══════════════════════════════     │
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  n8n OUT Workflows (ELO_Out_*) — includes Response Builder           │    │
│  │  • Receive from Core (unified format)                                │    │
│  │  • Response Builder: format for specific channel                     │    │
│  │     - Text length limits (Telegram 4096, WhatsApp 4096, Avito 2000) │    │
│  │     - Button formatting (inline, reply, quick reply)                 │    │
│  │     - Markdown/HTML conversion                                       │    │
│  │     - Attachment handling                                            │    │
│  │  • Call MCP adapter to send                                          │    │
│  │  • Save outgoing message to DB                                       │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                            ↓                                                 │
│  ┌─────────────────────────────────────────────────────────────────────┐    │
│  │  MCP ADAPTERS (send to messengers)                                   │    │
│  │  ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐ ┌─────────┐       │    │
│  │  │Telegram │ │WhatsApp │ │  Avito  │ │   VK    │ │   MAX   │       │    │
│  │  └─────────┘ └─────────┘ └─────────┘ └─────────┘ └─────────┘       │    │
│  └─────────────────────────────────────────────────────────────────────┘    │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

---

## System Flow (4 Contours)

```
┌─────────────────┐
│ CHANNEL CONTOUR │  ← IN: receive, normalize, transcribe
└────────┬────────┘
         ↓
┌─────────────────┐
│  INPUT CONTOUR  │  ← Tenant Resolver, Queue, Batcher
└────────┬────────┘
         ↓
┌─────────────────┐
│ CLIENT CONTOUR  │  ← Client Resolver, Dialog Resolver
└────────┬────────┘
         ↓
┌─────────────────┐
│  CORE CONTOUR   │  ← Context Builder, Orchestrator, Dialog Engine
└────────┬────────┘
         ↓
┌─────────────────┐
│ CHANNEL CONTOUR │  ← OUT: Response Builder, send via MCP
└─────────────────┘
```

---

## MCP Adapters

| Adapter | Server | Port | Channels |
|---------|--------|------|----------|
| mcp-telegram | Finnish (217.145.79.27) | 8767 | Telegram bots |
| mcp-whatsapp | Finnish (217.145.79.27) | 8766 | WhatsApp (Wappi.pro) |
| mcp-avito | RU (45.144.177.128) | 8765 | Avito Messenger |
| mcp-vk | RU (45.144.177.128) | 8767 | VK Communities |
| mcp-max | RU (45.144.177.128) | 8768 | MAX (VK Teams) |
| mcp-form | RU (45.144.177.128) | 8770 | Web forms |

---

## IN Workflows

| Workflow | File | Purpose |
|----------|------|---------|
| ELO_In_Telegram | [ELO_In_Telegram.md](workflows_info/ELO_In_Telegram.md) | Telegram messages |
| ELO_In_WhatsApp | [ELO_In_WhatsApp.md](workflows_info/ELO_In_WhatsApp.md) | WhatsApp messages |
| ELO_In_Avito | [ELO_In_Avito.md](workflows_info/ELO_In_Avito.md) | Avito messages |
| ELO_In_VK | [ELO_In_VK.md](workflows_info/ELO_In_VK.md) | VK messages |
| ELO_In_MAX | [ELO_In_MAX.md](workflows_info/ELO_In_MAX.md) | MAX messages |
| ELO_In_Form | [ELO_In_Form.md](workflows_info/ELO_In_Form.md) | Web form submissions |
| ELO_In_Phone | [ELO_In_Phone.md](workflows_info/ELO_In_Phone.md) | Phone calls |

---

## OUT Workflows (includes Response Builder)

| Workflow | File | Purpose |
|----------|------|---------|
| ELO_Out_Telegram | [ELO_Out_Telegram.md](workflows_info/ELO_Out_Telegram.md) | Format + send to Telegram |
| ELO_Out_WhatsApp | [ELO_Out_WhatsApp.md](workflows_info/ELO_Out_WhatsApp.md) | Format + send to WhatsApp |
| ELO_Out_Avito | [ELO_Out_Avito.md](workflows_info/ELO_Out_Avito.md) | Format + send to Avito |
| ELO_Out_VK | [ELO_Out_VK.md](workflows_info/ELO_Out_VK.md) | Format + send to VK |
| ELO_Out_MAX | [ELO_Out_MAX.md](workflows_info/ELO_Out_MAX.md) | Format + send to MAX |

---

## Response Builder (part of OUT workflows)

Response Builder is **embedded in each OUT workflow**, not a separate block.

**What it does:**

| Task | Description |
|------|-------------|
| **Text formatting** | Markdown → channel-specific format |
| **Length limits** | Split long messages if needed |
| **Button conversion** | Unified buttons → channel-specific (inline, reply, quick) |
| **Attachment handling** | Convert URLs to channel format |

**Channel limits:**

| Channel | Text limit | Buttons | Markdown |
|---------|-----------|---------|----------|
| Telegram | 4096 chars | Inline + Reply keyboard | ✅ HTML/Markdown |
| WhatsApp | 4096 chars | Quick Reply (3 max) | ✅ Limited |
| Avito | 2000 chars | ❌ | ❌ Plain text |
| VK | 4096 chars | Keyboard | ✅ Limited |
| MAX | 4096 chars | Buttons | ✅ |

---

## Contracts

### IN: Normalized Message (MCP → n8n)

```json
{
  "chat_id": "123456789",
  "user_id": "987654321",
  "text": "Message text",
  "message_id": "54321",
  "timestamp": "2024-12-10T10:30:00Z",
  "bot_token": "123456:ABC-DEF...",
  "first_name": "Ivan",
  "last_name": "Petrov",
  "username": "ivanpetrov",
  "attachments": [
    {"type": "voice", "file_id": "...", "duration": 5},
    {"type": "photo", "file_id": "..."}
  ],
  "is_callback": false,
  "callback_data": null
}
```

### IN: ELO Core Contract (n8n → MCP-INPUT)

```json
{
  "channel": "telegram",
  "bot_token": "123456:ABC...",
  "external_user_id": "tg_987654321",
  "external_chat_id": "tg_123456789",
  "text": "Message text (with transcription if voice)",
  "timestamp": "2024-12-10T10:30:00Z",
  "client_phone": null,
  "client_name": "Ivan Petrov",
  "message_id": "54321",
  "trace_id": "trace_abc123",
  "media": {
    "has_voice": true,
    "voice_transcribed_text": "transcription",
    "has_images": false,
    "images": []
  },
  "meta": {
    "external_message_id": "54321",
    "provider": "mcp-telegram"
  }
}
```

### OUT: From Core (Core → n8n OUT)

```json
{
  "tenant_id": "uuid",
  "dialog_id": "uuid",
  "channel_id": 1,
  "external_chat_id": "tg_123456789",
  "message": {
    "text": "Response text",
    "buttons": [
      {"text": "Yes", "callback_data": "yes"},
      {"text": "No", "callback_data": "no"}
    ],
    "attachments": []
  },
  "trace_id": "trace_abc123"
}
```

### OUT: To MCP Adapter (n8n → MCP)

```json
{
  "chat_id": "123456789",
  "text": "Response text",
  "reply_markup": {
    "inline_keyboard": [[
      {"text": "Yes", "callback_data": "yes"},
      {"text": "No", "callback_data": "no"}
    ]]
  }
}
```

---

## Channel-Specific Features

| Channel | Voice IN | Images | Buttons | Location | Special |
|---------|----------|--------|---------|----------|---------|
| Telegram | ✅ Whisper | ✅ | ✅ Inline/Reply | ✅ | Callbacks |
| WhatsApp | ✅ Whisper | ✅ | ✅ Quick Reply | ✅ | Templates |
| Avito | ❌ | ✅ | ❌ | ❌ | Item context |
| VK | ✅ | ✅ | ✅ | ❌ | Keyboard |
| MAX | ✅ | ✅ | ✅ | ❌ | - |
| Form | ❌ | ✅ | ❌ | ❌ | One-shot |
| Phone | ✅ STT | ❌ | ❌ | ❌ | Call transcription |

---

## Voice Transcription

All channels with voice support use **OpenAI Whisper**.

```
Voice message → Download file → OpenAI Whisper → Text
                                      ↓
                              Append to message text
```

---

## Dependencies

| Type | Resource | Purpose |
|------|----------|---------|
| Credentials | OpenAI | Voice transcription |
| Workflow | ELO_Core_Tenant_Resolver | Determine tenant (IN flow) |
| External | Telegram Bot API | Download voice, send messages |
| External | Wappi.pro API | WhatsApp integration |
| Service | MCP-INPUT | Receive normalized messages |

---

**Document:** CHANNEL_CONTOUR_OVERVIEW.md
**Date:** 2025-12-11
**Author:** Dmitry + Claude
**Status:** Complete (IN + OUT + Response Builder)
