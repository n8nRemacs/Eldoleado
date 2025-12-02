const fs = require('fs');
const data = JSON.parse(fs.readFileSync('c:/Users/User/Dropbox/BattCRMApp/n8n_workflows_backup/API Backend/API_Operator_Appeal_Detail.json', 'utf8'));

// Prepare payload for n8n API
const payload = {
  name: "API_Android_Appeal_Detail",  // Keep existing name
  nodes: data.nodes,
  connections: data.connections,
  settings: { executionOrder: "v1" }
};

fs.writeFileSync('c:/Users/User/Dropbox/BattCRMApp/temp_appeal_detail_payload.json', JSON.stringify(payload, null, 2));
console.log('Payload saved');
