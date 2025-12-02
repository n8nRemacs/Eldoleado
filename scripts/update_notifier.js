const fs = require('fs');
const data = JSON.parse(fs.readFileSync('c:/Users/User/Dropbox/BattCRMApp/temp_notifier.json', 'utf8'));

// Find and update the Code in JavaScript1 node
const jsNode = data.nodes.find(n => n.name === 'Code in JavaScript1');
if (jsNode) {
  jsNode.parameters.jsCode = `return {
  tenant_id: $json.tenant_id,
  notification_type: 'new_appeal',
  appeal_id: $json.appeal_id || $json.appeal?.id,
  title: '🔔 Новое обращение',
  body: \`\${$json.client?.name || 'Клиент'} - \${$json.operator_notification?.channel || 'канал'}\`,
  data: {
    client_name: $json.client?.name || '',
    channel: $json.operator_notification?.channel || '',
    ai_response: $json.operator_notification?.original_response_text || $json.response_text || ''
  },
  // Pass extracted_data for FCM
  extracted_data: $json.extracted_data || {},
  response_text: $json.response_text || $json.operator_notification?.original_response_text || ''
};`;
  console.log('Updated Code in JavaScript1');
}

// Save updated workflow
const payload = {
  name: data.name,
  nodes: data.nodes,
  connections: data.connections,
  settings: data.settings
};
fs.writeFileSync('c:/Users/User/Dropbox/BattCRMApp/temp_notifier_updated.json', JSON.stringify(payload, null, 2));
console.log('Saved to temp_notifier_updated.json');
