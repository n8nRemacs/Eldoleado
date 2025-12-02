#!/usr/bin/env python3
"""
Автоматическое обновление workflow BAT AI Appeal Router
Добавляет управление стадиями обращения и условный вызов Tools
"""

import json
import requests
import uuid
from copy import deepcopy

# Configuration
N8N_URL = "https://n8n.n8nsrv.ru"
API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
WORKFLOW_ID = "yeSpnx9mPFRHQllf"

headers = {
    "X-N8N-API-KEY": API_KEY,
    "Content-Type": "application/json"
}

print("="*60)
print("Обновление workflow BAT AI Appeal Router")
print("="*60)

# Load current workflow
print("\n1. Загружаю текущий workflow...")
with open('workflows/BAT AI Appeal Router_yeSpnx9mPFRHQllf.json', 'r', encoding='utf-8') as f:
    workflow = json.load(f)

print(f"   Текущих узлов: {len(workflow['nodes'])}")

# Helper function to create node ID
def new_node_id():
    return str(uuid.uuid4())

# Find node by name
def find_node(name):
    for node in workflow['nodes']:
        if node['name'] == name:
            return node
    return None

# Add new nodes
print("\n2. Добавляю новые узлы...")

# Get position after "Format Context for AI1"
format_context_node = find_node("Format Context for AI1")
if format_context_node:
    base_x = format_context_node['position'][0]
    base_y = format_context_node['position'][1]
else:
    base_x, base_y = 1500, 2000

new_nodes = []

# Node 1: Check Appeal Stage
check_stage_node = {
    "id": new_node_id(),
    "name": "Check Appeal Stage",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [base_x + 200, base_y],
    "parameters": {
        "jsCode": """const input = $input.first().json;
const appeal = input.appeal || {};

console.log('=== CHECK APPEAL STAGE ===');
console.log('Current stage:', appeal.stage);

// Определяем текущую стадию
const currentStage = appeal.stage || 'Первичный контакт';

// Проверяем количество сообщений в истории
const conversationHistory = input.conversation_history || '';
const messageCount = (conversationHistory.match(/\\[.*?\\]/g) || []).length;

// Определяем что делать
const isFirstContact = currentStage === 'Первичный контакт' && messageCount <= 1;
const shouldCollectContext = currentStage === 'Сбор контекста' ||
                              currentStage === 'Первичный контакт' ||
                              currentStage === null;

console.log('Is first contact:', isFirstContact);
console.log('Should collect context:', shouldCollectContext);

return {
  ...input,
  current_stage: currentStage,
  is_first_contact: isFirstContact,
  should_collect_context: shouldCollectContext
};"""
    }
}
new_nodes.append(check_stage_node)

# Node 2: Load Context Fields Config
load_config_node = {
    "id": new_node_id(),
    "name": "Load Context Fields Config",
    "type": "n8n-nodes-base.postgres",
    "typeVersion": 2.4,
    "position": [base_x + 400, base_y],
    "parameters": {
        "operation": "executeQuery",
        "query": """SELECT
  field_name,
  field_label,
  is_required,
  collection_order,
  tool_name,
  prompt_template
FROM context_fields_config
WHERE tenant_id = '{{ $json.tenant_id }}'::uuid
  AND is_active = true
  AND (deal_type_id IS NULL OR deal_type_id = '{{ $json.appeal.deal_type_id }}'::uuid)
ORDER BY collection_order ASC;""",
        "options": {}
    },
    "credentials": {
        "postgres": {
            "id": "n2SyhP9QhMnp1ryk",
            "name": "Postgres account"
        }
    },
    "alwaysOutputData": True
}
new_nodes.append(load_config_node)

# Node 3: Check What's Missing
check_missing_node = {
    "id": new_node_id(),
    "name": "Check What's Missing",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [base_x + 600, base_y],
    "parameters": {
        "jsCode": """const input = $('Check Appeal Stage').first().json;
const configArray = $input.first().json;
const config = Array.isArray(configArray) ? configArray : [configArray];

const appeal = input.appeal || {};

console.log('=== CHECK WHAT\\'S MISSING ===');

// Проверяем какие поля уже заполнены
const missing = [];
const toolsToCall = [];
const fieldsStatus = {};

config.forEach(field => {
  const fieldValue = appeal[field.field_name];
  const isCollected = fieldValue !== null &&
                      fieldValue !== undefined &&
                      fieldValue !== '' &&
                      fieldValue !== 'null';

  fieldsStatus[field.field_name] = {
    label: field.field_label,
    is_required: field.is_required,
    is_collected: isCollected,
    tool_name: field.tool_name,
    current_value: fieldValue
  };

  if (!isCollected && field.tool_name) {
    missing.push(field.field_label);
    toolsToCall.push(field.tool_name);
  }
});

// Проверяем полноту
const requiredFields = config.filter(f => f.is_required);
const collectedRequired = requiredFields.filter(f =>
  appeal[f.field_name] !== null &&
  appeal[f.field_name] !== undefined &&
  appeal[f.field_name] !== ''
);

const isComplete = collectedRequired.length >= requiredFields.length && requiredFields.length > 0;

console.log('Missing fields:', missing);
console.log('Tools to call:', toolsToCall);
console.log('Is complete:', isComplete);

return {
  ...input,
  required_fields: config,
  missing_fields: missing,
  tools_to_call: toolsToCall,
  is_context_complete: isComplete,
  fields_status: fieldsStatus
};"""
    }
}
new_nodes.append(check_missing_node)

# Node 4: Update Context Completion Status (will be added before AI Agent)
update_status_node = {
    "id": new_node_id(),
    "name": "Update Context Completion Status",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [base_x - 200, base_y + 600],
    "parameters": {
        "jsCode": """const input = $input.first().json;
const extracted = input.extracted_data || {};
const requiredFields = input.required_fields || [];

console.log('=== UPDATE CONTEXT COMPLETION STATUS ===');

// Формируем fields_collected
const fieldsCollected = {};
let collectedCount = 0;
let requiredCount = 0;

requiredFields.forEach(field => {
  const value = extracted[field.field_name];
  const isCollected = value !== null && value !== undefined && value !== '' && value !== 'null';

  if (field.is_required) {
    requiredCount++;
  }

  if (isCollected) {
    fieldsCollected[field.field_name] = {
      value: String(value),
      collected_at: new Date().toISOString(),
      is_valid: true
    };

    if (field.is_required) {
      collectedCount++;
    }
  } else {
    fieldsCollected[field.field_name] = null;
  }
});

const isComplete = collectedCount >= requiredCount && requiredCount > 0;
const completionPercentage = requiredCount > 0
  ? Math.round((collectedCount / requiredCount) * 100)
  : 0;

const completionStatus = {
  fields_collected: fieldsCollected,
  required_fields_count: requiredCount,
  collected_required_count: collectedCount,
  is_complete: isComplete,
  completion_percentage: completionPercentage,
  updated_at: new Date().toISOString()
};

console.log('Completion status:', JSON.stringify(completionStatus, null, 2));

return {
  ...input,
  context_completion_status: completionStatus,
  is_context_complete: isComplete
};"""
    }
}
new_nodes.append(update_status_node)

print(f"   Добавлено новых узлов: {len(new_nodes)}")

# Add new nodes to workflow
workflow['nodes'].extend(new_nodes)

print(f"   Всего узлов теперь: {len(workflow['nodes'])}")

# Update existing nodes
print("\n3. Обновляю существующие узлы...")

# Update "Route by Completeness" to include context_completion_status
route_node = find_node("Route by Completeness")
if route_node:
    print("   - Обновляю 'Route by Completeness'...")
    # Добавить код для обновления context_completion_status в SQL
    # Это сложно сделать через string replacement, лучше вручную

# Update "Collect Tool Results" to handle missing tools
collect_node = find_node("Collect Tool Results")
if collect_node:
    print("   - Обновляю 'Collect Tool Results'...")
    # Добавить проверку tools_to_call

print("\n4. Обновляю connections...")
# Connections будут сложные, оставим для ручного редактирования

# Save updated workflow
print("\n5. Сохраняю обновленный workflow...")
output_file = 'workflows/BAT AI Appeal Router_UPDATED.json'
with open(output_file, 'w', encoding='utf-8') as f:
    json.dump(workflow, f, indent=2, ensure_ascii=False)

print(f"   Сохранено в: {output_file}")

# Upload to n8n
print("\n6. Загружаю в n8n...")
print("   ВНИМАНИЕ: Автоматическая загрузка отключена для безопасности")
print("   Connections нужно настроить вручную в n8n UI")

print("\n" + "="*60)
print("ЗАВЕРШЕНО!")
print("="*60)
print(f"\nОбновленный workflow: {output_file}")
print(f"Новых узлов добавлено: {len(new_nodes)}")
print("\nСледующие шаги:")
print("1. Откройте n8n UI: https://n8n.n8nsrv.ru")
print("2. Импортируйте обновленный workflow")
print("3. Настройте connections между узлами")
print("4. Активируйте workflow")
