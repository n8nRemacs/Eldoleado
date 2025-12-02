#!/usr/bin/env python3
import requests
import json
import uuid

print("="*70)
print("UPDATING BAT AI Appeal Router")
print("="*70)

wf_id = 'yeSpnx9mPFRHQllf'
api_key = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M'
headers = {'X-N8N-API-KEY': api_key, 'Content-Type': 'application/json'}

# Fetch
print("\n[1/6] Fetching workflow...")
r = requests.get(f'https://n8n.n8nsrv.ru/api/v1/workflows/{wf_id}', headers=headers)
wf = r.json()
print(f"   Loaded: {len(wf['nodes'])} nodes")

# Helper
def new_id():
    return str(uuid.uuid4())

def find_node(name):
    for n in wf['nodes']:
        if n['name'] == name:
            return n
    return None

# Add nodes
print("\n[2/6] Adding 4 new nodes...")
new_nodes = []

# Node 1: Check Appeal Stage
n1_id = new_id()
new_nodes.append({
    "id": n1_id,
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
const messageCount = (conversationHistory.match(/\\[.*?\\]/g) || []).length;

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
})

# Node 2: Load Context Fields Config
n2_id = new_id()
new_nodes.append({
    "id": n2_id,
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
})

# Node 3: Check What's Missing
n3_id = new_id()
new_nodes.append({
    "id": n3_id,
    "name": "Check What's Missing",
    "type": "n8n-nodes-base.code",
    "typeVersion": 2,
    "position": [2100, 1936],
    "parameters": {
        "jsCode": """const input = $('Check Appeal Stage').first().json;
const configArray = $input.first().json;
const config = Array.isArray(configArray) ? configArray : [configArray];
const appeal = input.appeal || {};

console.log('=== CHECK WHAT\\'S MISSING ===');

const missing = [];
const toolsToCall = [];
const fieldsStatus = {};

config.forEach(field => {
  const fieldValue = appeal[field.field_name];
  const isCollected = fieldValue !== null && fieldValue !== undefined && fieldValue !== '' && fieldValue !== 'null';

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

console.log('Missing:', missing);
console.log('Tools to call:', toolsToCall);

return {
  ...input,
  required_fields: config,
  missing_fields: missing,
  tools_to_call: toolsToCall,
  is_context_complete: isComplete,
  fields_status: fieldsStatus
};"""
    }
})

# Node 4: Update Context Completion Status
n4_id = new_id()
new_nodes.append({
    "id": n4_id,
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

console.log('Status:', JSON.stringify(completionStatus));

return {
  ...input,
  context_completion_status: completionStatus,
  is_context_complete: isComplete
};"""
    }
})

wf['nodes'].extend(new_nodes)
print(f"   Added {len(new_nodes)} nodes. Total: {len(wf['nodes'])}")

# Update connections
print("\n[3/6] Updating connections...")

if 'Prepare Session1' in wf['connections']:
    wf['connections']['Prepare Session1']['main'] = [[{"node": "Check Appeal Stage", "type": "main", "index": 0}]]

wf['connections']['Check Appeal Stage'] = {"main": [[{"node": "Load Context Fields Config", "type": "main", "index": 0}]]}
wf['connections']['Load Context Fields Config'] = {"main": [[{"node": "Check What's Missing", "type": "main", "index": 0}]]}
wf['connections']["Check What's Missing"] = {"main": [[{"node": "Attach Tools Base", "type": "main", "index": 0}]]}
wf['connections']['Collect Tool Results']['main'] = [[{"node": "Update Context Completion Status", "type": "main", "index": 0}]]
wf['connections']['Update Context Completion Status'] = {"main": [[{"node": "AI Agent1", "type": "main", "index": 0}]]}

print("   Connections configured")

# Update existing nodes
print("\n[4/6] Updating existing nodes...")

route_node = find_node("Route by Completeness")
if route_node:
    old_code = route_node['parameters']['jsCode']
    if 'context_completion_status' not in old_code:
        insert = """
// Add context_completion_status
if (input.context_completion_status) {
  const statusJson = JSON.stringify(input.context_completion_status).replace(/'/g, "''");
  setFields.push(`context_completion_status = '\${statusJson}'::jsonb`);
}

// Update stage
if (input.is_context_complete === true) {
  setFields.push(`stage = 'Обработка заявки'`);
} else if (input.current_stage === 'Первичный контакт') {
  setFields.push(`stage = 'Сбор контекста'`);
}

"""
        route_node['parameters']['jsCode'] = old_code.replace('return {', insert + 'return {')
        print("   Updated 'Route by Completeness'")

# Save locally
print("\n[5/6] Saving backup...")
with open('workflows/BAT AI Appeal Router_UPDATED.json', 'w', encoding='utf-8') as f:
    json.dump(wf, f, indent=2, ensure_ascii=False)
print("   Saved: workflows/BAT AI Appeal Router_UPDATED.json")

# Upload to n8n
print("\n[6/6] Uploading to n8n...")
payload = {
    "name": wf['name'],
    "nodes": wf['nodes'],
    "connections": wf['connections'],
    "settings": wf.get('settings', {}),
    "staticData": wf.get('staticData')
}

r = requests.put(f'https://n8n.n8nsrv.ru/api/v1/workflows/{wf_id}', headers=headers, json=payload)

if r.status_code == 200:
    print("   SUCCESS! Workflow updated")
    print(f"   Total nodes: {len(wf['nodes'])}")
    print("   Status: Inactive")
else:
    print(f"   ERROR: Status {r.status_code}")
    print(f"   Response: {r.text[:300]}")

print("\n" + "="*70)
print("DONE!")
print("="*70)
print("\nNext steps:")
print("1. Open: https://n8n.n8nsrv.ru")
print("2. Open workflow: BAT AI Appeal Router")
print("3. Verify connections")
print("4. Activate workflow")
