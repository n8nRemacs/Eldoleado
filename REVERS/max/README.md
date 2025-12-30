# MCP MAX Server

Multi-tenant User API proxy for MAX messenger (ex TamTam, VK Teams).

## Features

- Multi-account support via phone+SMS or saved token auth
- Full message operations (send, edit, delete, reactions)
- Chat management (create, delete, members, typing)
- Real-time events forwarding to n8n
- Session persistence in Redis
- Health checks and monitoring

## API Endpoints

### Auth

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/v1/auth/start` | Start SMS authentication |
| POST | `/v1/auth/verify` | Verify SMS code |
| POST | `/v1/auth/verify-2fa` | Verify 2FA password |
| POST | `/v1/auth/login` | Login by saved token |
| POST | `/v1/auth/logout` | Logout account |
| GET | `/v1/accounts` | List connected accounts |
| DELETE | `/v1/accounts/{phone}` | Remove account |

### Messages

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/v1/messages/send` | Send message |
| PUT | `/v1/messages/edit` | Edit message |
| DELETE | `/v1/messages` | Delete messages |
| POST | `/v1/messages/typing` | Set typing indicator |
| POST | `/v1/messages/read` | Mark as read |

### Chats

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/chats` | Get chat list |
| GET | `/v1/chats/{id}` | Get chat with history |
| POST | `/v1/chats` | Create chat |
| DELETE | `/v1/chats/{id}` | Delete chat |
| GET | `/v1/chats/{id}/members` | Get members |

### Contacts

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/contacts` | Get contact list |
| GET | `/v1/contacts/search` | Search contacts |

### Profile

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/v1/profile` | Get profile |
| PUT | `/v1/profile` | Update profile |

## Quick Start

### Docker

```bash
# Copy env file
cp .env.example .env

# Edit .env with your settings
nano .env

# Start
docker compose up -d

# Check logs
docker compose logs -f
```

### Local

```bash
# Install dependencies
pip install -r requirements.txt

# Copy and edit env
cp .env.example .env

# Run
python app.py
```

## Usage Example

### 1. Start Auth

```bash
curl -X POST http://localhost:8768/v1/auth/start \
  -H "Content-Type: application/json" \
  -d '{"phone": "+79001234567"}'
```

Response:
```json
{
  "status": "sms_sent",
  "phone_hash": "a1b2c3d4e5f6g7h8",
  "token": "auth_token_here"
}
```

### 2. Verify SMS Code

```bash
curl -X POST http://localhost:8768/v1/auth/verify \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+79001234567",
    "token": "auth_token_here",
    "code": "123456"
  }'
```

Response:
```json
{
  "status": "authenticated",
  "phone_hash": "a1b2c3d4e5f6g7h8",
  "profile": {"userId": 123, "firstName": "Name"},
  "login_token": "save_this_token",
  "device_id": "save_this_id"
}
```

### 3. Send Message

```bash
curl -X POST http://localhost:8768/v1/messages/send \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+79001234567",
    "chat_id": 456789,
    "text": "Hello from MCP!"
  }'
```

### 4. Reconnect with Token

```bash
curl -X POST http://localhost:8768/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "phone": "+79001234567",
    "token": "saved_login_token",
    "device_id": "saved_device_id"
  }'
```

## Event Forwarding

Incoming messages are forwarded to n8n webhook:

```json
{
  "channel": "max",
  "event_type": "message",
  "phone": "+79001234567",
  "phone_hash": "a1b2c3d4e5f6g7h8",
  "timestamp": "2024-12-29T12:00:00Z",
  "data": {
    "message_id": "msg123",
    "chat_id": 456789,
    "sender_id": 111222,
    "sender_name": "John Doe",
    "text": "Hello!",
    "attaches": []
  }
}
```

## Protocol Reference

See [MAX_USER_API.md](../Max-user/MAX_USER_API.md) for full protocol documentation.

## Limitations

- MAX blocks SMS auth from some server IPs
- Workaround: auth from local machine, then use token on server
- Session tokens expire after ~30 days of inactivity
