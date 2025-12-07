# MCP WhatsApp Baileys

WhatsApp API server using [Baileys](https://github.com/WhiskeySockets/Baileys) library. Multi-tenant, free alternative to Wappi.pro.

## Features

- **Messages**: text, image, video, audio, document, sticker, location, contact, reaction
- **Actions**: mark as read, typing indicator, check number
- **Calls**: detect incoming calls, auto-reject
- **Groups**: get info, send messages
- **Multi-session**: multiple WhatsApp accounts
- **Webhooks**: send events to n8n

## Quick Start

```bash
# Install dependencies
npm install

# Development
npm run dev

# Build & run
npm run build
npm start
```

## Docker

```bash
docker-compose up -d
```

## API Endpoints

### Sessions

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /sessions | List all sessions |
| POST | /sessions | Create new session |
| GET | /sessions/:id | Get session info |
| GET | /sessions/:id/qr | Get QR code |
| GET | /sessions/:id/status | Get status |
| DELETE | /sessions/:id | Delete session |

### Messages

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /messages/text | Send text |
| POST | /messages/image | Send image |
| POST | /messages/video | Send video |
| POST | /messages/audio | Send voice message |
| POST | /messages/document | Send document |
| POST | /messages/sticker | Send sticker |
| POST | /messages/location | Send location |
| POST | /messages/contact | Send contact |
| POST | /messages/reaction | Send reaction |
| POST | /messages/upload | Upload and send file |

### Actions

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /actions/read | Mark as read |
| POST | /actions/typing | Send typing indicator |
| POST | /actions/reject-call | Reject incoming call |
| POST | /actions/check-number | Check if on WhatsApp |
| GET | /actions/profile-picture | Get profile picture |

### Webhook (for n8n)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /webhook/whatsapp/:hash | Trigger action by hash |

## Usage Examples

### Create Session

```bash
curl -X POST http://localhost:8766/sessions \
  -H "Content-Type: application/json" \
  -d '{"webhookUrl": "https://n8n.example.com/webhook/wa"}'
```

Response:
```json
{
  "success": true,
  "data": {
    "sessionId": "uuid",
    "hash": "abc123",
    "webhookPath": "/webhook/whatsapp/abc123"
  }
}
```

### Get QR Code

```bash
curl http://localhost:8766/sessions/{sessionId}/qr
```

Response contains `qrImage` (base64 data URL) - display in browser to scan.

### Send Text Message

```bash
curl -X POST http://localhost:8766/messages/text \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "uuid",
    "to": "79001234567",
    "text": "Hello from Baileys!"
  }'
```

### Send Image

```bash
curl -X POST http://localhost:8766/messages/image \
  -H "Content-Type: application/json" \
  -d '{
    "sessionId": "uuid",
    "to": "79001234567",
    "mediaUrl": "https://example.com/image.jpg",
    "caption": "Check this out!"
  }'
```

## Webhook Events

Incoming events are sent to the configured `webhookUrl`:

```json
{
  "event": "message",
  "sessionId": "uuid",
  "sessionHash": "abc123",
  "timestamp": 1699999999999,
  "data": {
    "messageId": "...",
    "from": "79001234567",
    "fromName": "John",
    "type": "text",
    "text": "Hello!",
    "timestamp": 1699999999999
  }
}
```

Event types: `message`, `call`, `presence`, `connection.update`

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| PORT | 8766 | Server port |
| HOST | 0.0.0.0 | Server host |
| REDIS_URL | - | Redis for session persistence |
| DEFAULT_WEBHOOK_URL | - | Default webhook for all sessions |
| API_KEY | - | Optional API key for auth |
| LOG_LEVEL | info | Logging level |

## License

MIT
