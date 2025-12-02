#!/usr/bin/env python3
"""Автоматическое обновление BAT AI Appeal Router"""
import json
import uuid
import requests

# Config
N8N_URL = "https://n8n.n8nsrv.ru"
API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
WORKFLOW_ID = "yeSpnx9mPFRHQllf"

print("="*70)
print("AUTO-UPDATE: BAT AI Appeal Router")
print("="*70)

# Load workflow
print("\n1. Loading workflow...")
with open('workflows/BAT AI Appeal Router_yeSpnx9mPFRHQllf.json', 'r', encoding='utf-8') as f:
    wf = json.load(f)

print(f"   Current nodes: {len(wf['nodes'])}")

# Helper functions
def new_id():
    return str(uuid.uuid4())

def find_node(name):
    for n in wf['nodes']:
        if n['name'] == name:
            return n
    return None

# Add new nodes
print("\n2. Adding new nodes...")

new_nodes = []

# Node 1: Check Appeal Stage
node1 = {
    "id": new_id(),
    "name": "Check Appeal Stage",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [1700, 1936],
    "parameters": {
        "jsCode": """const input = $input.first().json;
const appeal = input.appeal || {};

console.log('=== CHECK APPEAL STAGE ===');
console.log('Current stage:', appeal.stage);

const currentStage = appeal.stage || 'Первичный контакт';
const conversationHistory = input.conversation_history || '';
const messageCount = (conversationHistory.match(/\[.*?\]/g) || []).length;

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
new_nodes.append(node1)

# Node 2: Load Context Fields Config  
node2 = {
    "id": new_id(),
    "name": "Load Context Fields Config",
    "type": "n8n-nodes-base.postgres",
    "typeVersion": 2.4,
    "position": [1900, 1936],
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
new_nodes.append(node2)

# Node 3: Check What's Missing
node3 = {
    "id": new_id(),
    "name": "Check What's Missing",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [2100, 1936],
    "parameters": {
        "jsCode": """const input = $('Check Appeal Stage').first().json;
const configArray = $input.first().json;
const config = Array.isArray(configArray) ? configArray : [configArray];

const appeal = input.appeal || {};

console.log('=== CHECK WHAT\'S MISSING ===');

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
new_nodes.append(node3)

# Node 4: Update Context Completion Status
node4 = {
    "id": new_id(),
    "name": "Update Context Completion Status",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [2464, 2288],
    "parameters": {
        "jsCode": """const input = $input.first().json;
const extracted = input.extracted_data || {};
const requiredFields = input.required_fields || [];

console.log('=== UPDATE CONTEXT COMPLETION STATUS ===');

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
new_nodes.append(node4)

# Add to workflow
wf['nodes'].extend(new_nodes)
print(f"   Added 4 new nodes. Total: {len(wf['nodes'])}")

# Update connections
print("\n3. Updating connections...")

# Get node IDs
prepare_session_node = find_node("Prepare Session1")
attach_tools_node = find_node("Attach Tools Base")
collect_results_node = find_node("Collect Tool Results")
ai_agent_node = find_node("AI Agent1")

if not all([prepare_session_node, attach_tools_node, collect_results_node, ai_agent_node]):
    print("ERROR: Required nodes not found!")
    exit(1)

# Update connections dict
if 'connections' not in wf:
    wf['connections'] = {}

# Prepare Session1 → Check Appeal Stage
wf['connections']['Prepare Session1'] = {
    "main": [[{
        "node": "Check Appeal Stage",
        "type": "main",
        "index": 0
    }]]
}

# Check Appeal Stage → Load Context Fields Config
wf['connections']['Check Appeal Stage'] = {
    "main": [[{
        "node": "Load Context Fields Config",
        "type": "main",
        "index": 0
    }]]
}

# Load Context Fields Config → Check What's Missing
wf['connections']['Load Context Fields Config'] = {
    "main": [[{
        "node": "Check What's Missing",
        "type": "main",
        "index": 0
    }]]
}

# Check What's Missing → Attach Tools Base
wf['connections']['
