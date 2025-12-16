# Tunnel Server

Серверная часть омниканального мессенджера.

## Логика работы

**Все сообщения клиентов идут оператору. AI не отвечает автоматически.**

```
Клиент → Оператор:
├── Текст: сохраняем → уведомляем оператора
└── Аудио: Whisper API → транскрипция → оператору
    (оператор может слушать или читать)

Оператор → Клиент:
├── Текст: нормализуем → отправляем сразу
└── Голос: Android транскрибирует → нормализуем → утверждение → отправляем
```

## API для оператора

### Отправка текста (сразу)
```
POST /api/send
{
  "tenant_id": "...",
  "channel": "telegram",
  "chat_id": "123",
  "text": "привет"
}
→ нормализуется и отправляется сразу
```

### Отправка голоса (с утверждением)
```
1. POST /api/operator/normalize
{
  "tenant_id": "...",
  "channel": "telegram",
  "chat_id": "123",
  "text": "привет как дела",  // транскрибировано Android
  "is_voice": true
}
Response:
{
  "message_id": "uuid",
  "original_text": "привет как дела",
  "normalized_text": "Привет, как дела?"
}

2. POST /api/operator/approve
{
  "message_id": "uuid",
  "final_text": "Привет, как дела?"  // опционально, если исправил
}
→ отправляется клиенту
```

## Архитектура

```
┌─────────────────────────────────────────────────────────────┐
│                     TUNNEL SERVER                            │
├─────────────────────────────────────────────────────────────┤
│  INPUT LAYER                                                │
│  ├── WebSocket Manager (телефоны)      /ws                  │
│  ├── API Gateway (операторы)           /api/*               │
│  └── Webhook Receiver (n8n)            /webhook/*           │
├─────────────────────────────────────────────────────────────┤
│  PRE-AI PIPELINE                                            │
│  ├── Normalizer (унификация форматов)                       │
│  ├── Context Builder (PostgreSQL + Neo4j)                   │
│  └── Router (tenant → channel → dialog)                     │
├──────────────────────┬──────────────────────────────────────┤
│    ┌─────────────────▼─────────────────┐                    │
│    │         [AI CORE]                 │                    │
│    │  ├── Whisper API (клиент аудио)   │                    │
│    │  └── LLM API (нормализация)       │                    │
│    └─────────────────┬─────────────────┘                    │
├──────────────────────▼──────────────────────────────────────┤
│  POST-AI PIPELINE                                           │
│  ├── DB Logger                                              │
│  ├── Notifier (уведомление оператора)                       │
│  └── Action Executor (отправка)                             │
└─────────────────────────────────────────────────────────────┘
```

## AI Core

AI Core использует внешние API:
- **Whisper API** (OpenAI/Groq): транскрипция аудио клиентов
- **LLM API** (OpenRouter/OpenAI): нормализация текста оператора

```env
AI_CORE_ENABLED=true
AI_CORE_TRANSCRIPTION_URL=https://api.openai.com/v1/audio/transcriptions
AI_CORE_NORMALIZATION_URL=https://openrouter.ai/api/v1/chat/completions
```

## Что хранится в БД

| Сообщение | Сохраняется |
|-----------|-------------|
| Клиент текст | `text` |
| Клиент аудио | `transcription`, `audio_url` |
| Оператор текст | `normalized_text` |
| Оператор голос | `text` (оригинал), `normalized_text` |

## Структура

```
tunnel-server/
├── app/
│   ├── config.py
│   ├── models.py
│   ├── input/
│   │   ├── websocket_manager.py
│   │   ├── api.py
│   │   └── webhooks.py
│   └── pipeline/
│       ├── orchestrator.py
│       ├── pre_ai/
│       ├── ai_core/
│       └── post_ai/
├── main.py
└── requirements.txt
```

## Запуск

```bash
cd tunnel-server
cp .env.example .env
./start.sh
```
