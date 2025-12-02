#!/usr/bin/env python3
"""Extract workflow IDs and webhooks from n8n API and local files"""

import json
import requests
from pathlib import Path
import urllib3

# Disable SSL warnings for local n8n instance
urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
N8N_URL = "https://n8n.n8nsrv.ru"


def get_workflows_from_api():
    """Get all workflows from n8n API"""
    headers = {
        "X-N8N-API-KEY": API_KEY,
        "Accept": "application/json"
    }

    response = requests.get(
        f"{N8N_URL}/api/v1/workflows",
        headers=headers,
        verify=False
    )

    if response.status_code == 200:
        return response.json().get('data', [])
    else:
        print(f"Error getting workflows: {response.status_code}")
        return []


def extract_webhooks_from_workflow(workflow_data):
    """Extract webhook paths from workflow nodes"""
    webhooks = []

    if 'nodes' in workflow_data:
        for node in workflow_data['nodes']:
            if node.get('type') == 'n8n-nodes-base.webhook':
                params = node.get('parameters', {})
                path = params.get('path', '')
                method = params.get('httpMethod', 'GET')
                webhooks.append({
                    'node_name': node.get('name'),
                    'path': path,
                    'method': method,
                    'full_url': f"{N8N_URL}/webhook/{path}" if path else None
                })

    return webhooks


def analyze_workflows():
    """Analyze workflows from API and local files"""
    print("=" * 80)
    print("WORKFLOWS ANALYSIS")
    print("=" * 80)

    # Get workflows from API
    print("\n[API] Fetching workflows from n8n API...")
    api_workflows = get_workflows_from_api()

    results = []

    for wf in api_workflows:
        wf_info = {
            'source': 'API',
            'id': wf.get('id'),
            'name': wf.get('name'),
            'active': wf.get('active'),
            'createdAt': wf.get('createdAt'),
            'updatedAt': wf.get('updatedAt'),
            'webhooks': extract_webhooks_from_workflow(wf)
        }
        results.append(wf_info)

    print(f"[OK] Found {len(api_workflows)} workflows in API\n")

    # Get workflows from local files
    print("[LOCAL] Scanning local workflow files...")
    workflows_dir = Path('workflows')

    if workflows_dir.exists():
        local_files = list(workflows_dir.glob('*.json'))
        print(f"[OK] Found {len(local_files)} local workflow files\n")

        for file_path in local_files:
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    wf_data = json.load(f)

                    wf_info = {
                        'source': 'LOCAL',
                        'file': file_path.name,
                        'id': wf_data.get('id'),
                        'name': wf_data.get('name'),
                        'active': wf_data.get('active'),
                        'webhooks': extract_webhooks_from_workflow(wf_data)
                    }
                    results.append(wf_info)
            except Exception as e:
                print(f"[WARN] Error reading {file_path.name}: {e}")

    # Print results
    print("=" * 80)
    print("RESULTS")
    print("=" * 80)

    for wf in results:
        print(f"\n[{wf['source']}]: {wf['name']}")
        print(f"   ID: {wf['id']}")

        if wf['source'] == 'API':
            print(f"   Active: {'YES' if wf.get('active') else 'NO'}")
            print(f"   Updated: {wf.get('updatedAt', 'N/A')}")
        else:
            print(f"   File: {wf.get('file')}")

        if wf['webhooks']:
            print(f"   Webhooks ({len(wf['webhooks'])}):")
            for webhook in wf['webhooks']:
                print(f"      - {webhook['method']} /{webhook['path']}")
                if webhook['full_url']:
                    print(f"        {webhook['full_url']}")
        else:
            print(f"   Webhooks: None")

    # Save to JSON for easier processing
    output_file = 'workflows_summary.json'
    with open(output_file, 'w', encoding='utf-8') as f:
        json.dump(results, f, indent=2, ensure_ascii=False)

    print(f"\n{'=' * 80}")
    print(f"[OK] Summary saved to: {output_file}")
    print(f"{'=' * 80}")

    return results


if __name__ == "__main__":
    analyze_workflows()
