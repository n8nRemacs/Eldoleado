#!/bin/bash
# Test Block 2: Context Collection

# Test 1: Full context collection via orchestrator
echo "=== Test 1: ELO_Context_Collector (Block 2 Orchestrator) ==="
curl -X POST "https://n8n.n8nsrv.ru/webhook/elo-core-ingest" \
  -H "Content-Type: application/json" \
  -d '{
    "tenant_id": 1,
    "client_id": "test-client-001",
    "dialog_id": "550e8400-e29b-41d4-a716-446655440000",
    "channel_id": 1,
    "channel_account_id": 1,
    "channel": "telegram",
    "external_chat_id": "123456789",
    "text": "Здравствуйте, у меня iPhone 13 Pro, разбился экран. Сколько стоит ремонт?",
    "is_new_client": false,
    "is_new_dialog": false
  }'

echo -e "\n\n"

# Test 2: Direct AI extraction
echo "=== Test 2: ELO_AI_Extract_v2 (direct) ==="
curl -X POST "https://n8n.n8nsrv.ru/webhook/elo-ai-extract-v2" \
  -H "Content-Type: application/json" \
  -d '{
    "message": "iPhone 13 Pro, экран разбит, нужен ремонт",
    "tenant_id": 1,
    "dialog_id": "550e8400-e29b-41d4-a716-446655440000",
    "context": {},
    "trace_id": "test_extract_001"
  }'

echo -e "\n\n"

# Test 3: Direct funnel controller
echo "=== Test 3: ELO_Funnel_Controller_v2 (direct) ==="
curl -X POST "https://n8n.n8nsrv.ru/webhook/elo-funnel-controller-v2" \
  -H "Content-Type: application/json" \
  -d '{
    "context": {
      "tenant_id": 1,
      "dialog_id": "550e8400-e29b-41d4-a716-446655440000",
      "client_id": "test-client-001",
      "current_stage": "lead",
      "vertical_id": 1,
      "stage_masks": {},
      "device_brand": "Apple",
      "device_model": "iPhone 13 Pro",
      "problem_description": "разбитый экран"
    },
    "message": { "text": "да, хочу записаться на ремонт" },
    "extracted_context": {
      "entities": [
        {"type": "device_brand", "value": "Apple"},
        {"type": "device_model", "value": "iPhone 13 Pro"},
        {"type": "problem", "value": "разбитый экран"}
      ]
    },
    "trace_id": "test_funnel_001"
  }'

echo -e "\n\nDone!"
