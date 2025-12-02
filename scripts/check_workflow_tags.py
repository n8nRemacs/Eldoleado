#!/usr/bin/env python3
"""Check if workflows have tags/folders in n8n API"""

import json
import requests
import urllib3

urllib3.disable_warnings(urllib3.exceptions.InsecureRequestWarning)

API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzYzNzk0ODY3fQ.UQZ740xA5qec8q3EM95CF-0wG5qx4GeVo1DVAEbVZ8M"
N8N_URL = "https://n8n.n8nsrv.ru"

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
    workflows = response.json().get('data', [])

    # Check first workflow for all available fields
    if workflows:
        print("All fields in first workflow:")
        print("=" * 60)
        for key in workflows[0].keys():
            print(f"  - {key}: {type(workflows[0][key]).__name__}")

        print("\n" + "=" * 60)
        print("Sample workflow data:")
        print("=" * 60)
        sample = workflows[0]
        for key, value in sample.items():
            if key not in ['nodes', 'connections', 'settings', 'staticData']:
                print(f"{key}: {value}")

        # Check if any workflows have tags
        print("\n" + "=" * 60)
        print("Checking for tags/folders:")
        print("=" * 60)

        workflows_with_tags = [w for w in workflows if w.get('tags') and len(w['tags']) > 0]

        print(f"Total workflows: {len(workflows)}")
        print(f"Workflows with tags: {len(workflows_with_tags)}")

        if workflows_with_tags:
            print("\nSample workflows with tags:")
            for wf in workflows_with_tags[:5]:
                print(f"\n  {wf['name']}:")
                print(f"    Tags: {wf['tags']}")

        # Get unique tags
        all_tags = set()
        for wf in workflows:
            if wf.get('tags'):
                for tag in wf['tags']:
                    if isinstance(tag, dict):
                        all_tags.add(tag.get('name', tag.get('id', str(tag))))
                    else:
                        all_tags.add(str(tag))

        if all_tags:
            print("\n" + "=" * 60)
            print("All unique tags/folders:")
            print("=" * 60)
            for tag in sorted(all_tags):
                count = sum(1 for w in workflows if tag in str(w.get('tags', '')))
                print(f"  - {tag} ({count} workflows)")
        else:
            print("\nNo tags found in any workflows")

else:
    print(f"Error: {response.status_code}")
