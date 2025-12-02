#!/usr/bin/env python3
"""Prepare workflows section for Start.md and Stop.md"""

import json
from datetime import datetime

def prepare_workflows_section():
    """Create workflows section for documentation"""

    # Read the summary
    with open('workflows_summary.json', 'r', encoding='utf-8') as f:
        workflows = json.load(f)

    # Separate API and local workflows
    api_workflows = [w for w in workflows if w['source'] == 'API']
    local_workflows = [w for w in workflows if w['source'] == 'LOCAL']

    # Get unique local workflow IDs
    local_ids = {w['id']: w for w in local_workflows}

    # Get active workflows
    active_workflows = [w for w in api_workflows if w.get('active')]

    # Key workflows we care about (in local files or mentioned in docs)
    key_workflow_ids = [
        'yeSpnx9mPFRHQllf',  # BAT AI Appeal Router
        'laevudT8uPlXjpap',  # BAT Appeal Manager
        'pmDPBdREgE5wf1Cn',  # BAT IN Telegram
        'DaKs09InHidK2phf',  # BAT Message Router
        'hwYfaLAKCwaWpoQk',  # BAT Universal Batcher
        'GYoNjYYdXSkAS1sL',  # BAT Telegram Bot Handler
    ]

    key_workflows = {wf['id']: wf for wf in api_workflows if wf['id'] in key_workflow_ids}

    # Create markdown sections
    md_output = []

    md_output.append("## 📊 N8N Workflows Overview")
    md_output.append("")
    md_output.append(f"**Дата обновления:** {datetime.now().strftime('%Y-%m-%d %H:%M')}")
    md_output.append("")
    md_output.append(f"**Всего workflows в n8n:** {len(api_workflows)}")
    md_output.append(f"**Активных workflows:** {len(active_workflows)}")
    md_output.append(f"**Локальных файлов:** {len(set(w['file'] for w in local_workflows))}")
    md_output.append("")

    # Key workflows section
    md_output.append("### 🔑 Ключевые Workflows")
    md_output.append("")

    for wf_id in key_workflow_ids:
        if wf_id in key_workflows:
            wf = key_workflows[wf_id]
            status = "✅ ACTIVE" if wf.get('active') else "❌ INACTIVE"
            md_output.append(f"#### {wf['name']}")
            md_output.append(f"- **ID:** `{wf['id']}`")
            md_output.append(f"- **Статус:** {status}")
            md_output.append(f"- **Обновлен:** {wf.get('updatedAt', 'N/A')}")

            # Check if we have local file
            if wf_id in local_ids:
                local_file = local_ids[wf_id].get('file', 'N/A')
                md_output.append(f"- **Локальный файл:** `workflows/{local_file}`")

            # Webhooks if any
            if wf.get('webhooks'):
                md_output.append(f"- **Webhooks:**")
                for webhook in wf['webhooks']:
                    md_output.append(f"  - `{webhook['method']} /{webhook['path']}`")
                    md_output.append(f"  - URL: `{webhook['full_url']}`")
            else:
                md_output.append(f"- **Webhooks:** Нет")

            md_output.append("")

    # Active workflows section
    md_output.append("### ✅ Активные Workflows")
    md_output.append("")

    for wf in active_workflows:
        md_output.append(f"- **{wf['name']}** (`{wf['id']}`)")

        if wf.get('webhooks'):
            for webhook in wf['webhooks']:
                md_output.append(f"  - `{webhook['method']} {webhook['full_url']}`")

    md_output.append("")

    # Quick reference section
    md_output.append("### 📝 Быстрый справочник")
    md_output.append("")
    md_output.append("**Основные webhook endpoints:**")
    md_output.append("")

    # Collect all webhooks
    all_webhooks = []
    for wf in api_workflows:
        if wf.get('webhooks'):
            for webhook in wf['webhooks']:
                all_webhooks.append({
                    'workflow': wf['name'],
                    'method': webhook['method'],
                    'path': webhook['path'],
                    'url': webhook['full_url'],
                    'active': wf.get('active')
                })

    # Group by type
    webhook_groups = {
        'API': [w for w in all_webhooks if w['path'].startswith('api/')],
        'Android': [w for w in all_webhooks if w['path'].startswith('android/')],
        'Channels IN': [w for w in all_webhooks if any(ch in w['path'] for ch in ['vk', 'whatsapp', 'telegram', 'avito', 'form', 'max', 'phone'])],
        'Channels OUT': [w for w in all_webhooks if w['path'].startswith('out-')],
        'Other': [w for w in all_webhooks if not any([
            w['path'].startswith('api/'),
            w['path'].startswith('android/'),
            w['path'].startswith('out-'),
            any(ch in w['path'] for ch in ['vk', 'whatsapp', 'telegram', 'avito', 'form', 'max', 'phone'])
        ])]
    }

    for group_name, webhooks in webhook_groups.items():
        if webhooks:
            md_output.append(f"**{group_name}:**")
            for w in sorted(webhooks, key=lambda x: x['path']):
                status = "✅" if w['active'] else "❌"
                md_output.append(f"- {status} `{w['method']} /{w['path']}` - {w['workflow']}")
            md_output.append("")

    # Save to file
    output = '\n'.join(md_output)

    with open('workflows_section.md', 'w', encoding='utf-8') as f:
        f.write(output)

    print("=" * 80)
    print("WORKFLOWS SECTION GENERATED")
    print("=" * 80)
    print(f"\nGenerated markdown section with:")
    print(f"  - {len(key_workflow_ids)} key workflows")
    print(f"  - {len(active_workflows)} active workflows")
    print(f"  - {len(all_webhooks)} total webhooks")
    print(f"\nSaved to: workflows_section.md")
    print("=" * 80)

    return output

if __name__ == "__main__":
    prepare_workflows_section()
