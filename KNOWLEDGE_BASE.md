# ELDOLEADO KNOWLEDGE BASE

*Auto-generated: 2025-12-09 13:12:27*

---

## Quick Stats

| Component Type | Count |
|----------------|-------|
| doc | 105 |
| workflow | 101 |
| table | 37 |
| script | 31 |
| android_class | 16 |
| mcp_server | 10 |
| android_activity | 3 |
| android_api | 3 |
| android_adapter | 3 |
| android_viewmodel | 2 |
| android_dao | 2 |
| android_repository | 2 |
| android_entity | 2 |
| android_api_service | 1 |
| android_fcm | 1 |
| **TOTAL** | **319** |

| Relation Type | Count |
|---------------|-------|
| documents | 208 |
| uses | 89 |
| calls | 77 |
| reads_from | 43 |
| references | 28 |
| depends_on | 5 |

---

## Workflows (101)

### API

#### API_Android_Appeal_Detail

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Appeal_Detail.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id`
- **Tags:** API, BattCRM

#### API_Android_Appeals_List

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Appeals_List.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/list`
- **Tags:** API, BattCRM

#### API_Android_Auth

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Auth.json`
- **Tech:** json
- **Webhook:** `android/auth/login`
- **Tags:** API, BattCRM

#### API_Android_Device_Create

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Device_Create.json`
- **Tech:** json
- **Webhook:** `android/appeal-devices`
- **Tags:** API, BattCRM

#### API_Android_Device_Delete

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Device_Delete.json`
- **Tech:** json
- **Webhook:** `android/appeal-devices/:id`
- **Tags:** API, BattCRM

#### API_Android_Device_Update

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Device_Update.json`
- **Tech:** json
- **Webhook:** `android/appeal-devices/:id`
- **Tags:** API, BattCRM

#### API_Android_Logout

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Logout.json`
- **Tech:** json
- **Webhook:** `android/logout`
- **Tags:** API, BattCRM

#### API_Android_Manage_Devices

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Manage_Devices.json`
- **Tech:** json
- **Webhook:** `api/appeal-devices`

#### API_Android_Manage_Repairs

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Manage_Repairs.json`
- **Tech:** json
- **Webhook:** `api/appeal-repairs`

#### API_Android_Normalize

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Normalize.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id/normalize`
- **Tags:** API, BattCRM

#### API_Android_Register_FCM

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Register_FCM.json`
- **Tech:** json
- **Webhook:** `android-register-fcm`
- **Tags:** API, BattCRM

#### API_Android_Reject

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Reject.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id/reject`
- **Tags:** API, BattCRM

#### API_Android_Repair_Create

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Repair_Create.json`
- **Tech:** json
- **Webhook:** `android/appeal-repairs`
- **Tags:** API, BattCRM

#### API_Android_Repair_Delete

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Repair_Delete.json`
- **Tech:** json
- **Webhook:** `android/appeal-repairs/:id`
- **Tags:** API, BattCRM

#### API_Android_Repair_Update

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Repair_Update.json`
- **Tech:** json
- **Webhook:** `android/appeal-repairs/:id`
- **Tags:** API, BattCRM

#### API_Android_Send_Promo

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Send_Promo.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/{id}/promo`
- **Tags:** API, BattCRM

#### API_Android_Send_Response

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Send_Response.json`
- **Tech:** json
- **Webhook:** `/api/android/appeals/{id}/send`
- **Tags:** API, BattCRM

#### API_Android_Take_Appeal

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Take_Appeal.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id/take`
- **Tags:** API, BattCRM

#### API_Android_Update_Appeal_Mode

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Update_Appeal_Mode.json`
- **Tech:** json
- **Webhook:** `android-update-appeal-mode`
- **Tags:** API, BattCRM

#### API Android Update Settings

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Android_Update_Settings.json`
- **Tech:** json
- **Webhook:** `android-update-settings`
- **Tags:** API, BattCRM

#### API_Operator_Appeal_Detail

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Appeal_Detail.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/:id`

#### API_Operator_Appeals_List

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Appeals_List.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/list`

#### API_Operator_Normalize

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Normalize.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/:id/normalize`

#### API_Operator_Reject

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Reject.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/:id/reject`

#### API_Operator_Send_Promo

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Send_Promo.json`
- **Tech:** json
- **Webhook:** `operator-send-promo`

#### API_Operator_Send_Response

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Send_Response.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id/send`
- **Tags:** API, BattCRM

#### API_Operator_Take_Appeal

*n8n workflow in API*

- **File:** `n8n_workflows\API\API_Operator_Take_Appeal.json`
- **Tech:** json
- **Webhook:** `api/operator/appeals/:id/take`

#### Api_Voice_Uploader

*n8n workflow in API*

- **File:** `n8n_workflows\API\Api_Voice_Uploader.json`
- **Tech:** json
- **Webhook:** `api/voice/upload`
- **Tags:** API, BattCRM

### Core

#### BAT_AI_Appeal_Router

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_AI_Appeal_Router.json`
- **Tech:** json

#### BAT Appeal Manager

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Appeal_Manager.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Avito Token Refresher

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Avito_Token_Refresher.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Client Creator

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Client_Creator.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Client Resolver

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Client_Resolver.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Client Response Sender

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Client_Response_Sender.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Completeness Checker

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Completeness_Checker.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Disambiguation Handler

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Disambiguation_Handler.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT_FCM_Sender

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_FCM_Sender.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Fingerprint Tracker

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Fingerprint_Tracker.json`
- **Tech:** json
- **Webhook:** `fingerprint/track`
- **Tags:** BattCRM, Core

#### BAT Message Router

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Message_Router.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Neo4j CRUD

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Neo4j_CRUD.json`
- **Tech:** json
- **Webhook:** `neo4j/crud`
- **Tags:** BattCRM, Core

#### BAT Neo4j Sync

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Neo4j_Sync.json`
- **Tech:** json
- **Webhook:** `neo4j/sync`
- **Tags:** BattCRM, Core

#### BAT Neo4j Touchpoint Register

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Neo4j_Touchpoint_Register.json`
- **Tech:** json
- **Webhook:** `neo4j/touchpoint/register`
- **Tags:** BattCRM, Core

#### BAT Neo4j Touchpoint Tracker

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Neo4j_Touchpoint_Tracker.json`
- **Tech:** json
- **Webhook:** `neo4j/touchpoint`
- **Tags:** BattCRM, Core

#### BAT Operator Notifier

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Operator_Notifier.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Operator Response Handler 1 - Main Router

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Operator_Response_Handler_1_-_Main_Router.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Operator Response Handler 3 - Text Voice Normalize

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Operator_Response_Handler_3_-_Text_Voice_Normalize.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Short Link Manager

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Short_Link_Manager.json`
- **Tech:** json
- **Webhook:** `short-link/create`
- **Tags:** BattCRM, Core

#### BAT Telegram Bot Handler

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Telegram_Bot_Handler.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT_Tenant_Resolver

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Tenant_Resolver.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT Universal Batcher

*n8n workflow in Core*

- **File:** `n8n_workflows\Core\BAT_Universal_Batcher.json`
- **Tech:** json
- **Tags:** BattCRM, Core

### Import

#### StCRM OUT.Telegram (Adapter)

*n8n workflow in Import*

- **File:** `n8n_workflows\StCRM_OUT_Telegram_Adapter.json`
- **Tech:** json
- **Webhook:** `out-telegram`

### In

#### BAT IN Avito

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_Avito.json`
- **Tech:** json
- **Webhook:** `avito`
- **Tags:** BattCRM, In

#### BAT IN Form

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_Form.json`
- **Tech:** json
- **Webhook:** `form`
- **Tags:** BattCRM, In

#### BAT IN MAX

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_MAX.json`
- **Tech:** json
- **Webhook:** `max`
- **Tags:** BattCRM, In

#### BAT IN Phone

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_Phone.json`
- **Tech:** json
- **Webhook:** `phone`
- **Tags:** BattCRM, In

#### BAT IN Telegram

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_Telegram.json`
- **Tech:** json
- **Webhook:** `telegram-in`
- **Tags:** BattCRM, In

#### BAT_IN_Telegram

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_Telegram.json`
- **Tech:** json
- **Webhook:** `telegram-in`
- **Tags:** BattCRM, In

#### BAT IN VK

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_VK.json`
- **Tech:** json
- **Webhook:** `vk`
- **Tags:** BattCRM, In

#### BAT IN WhatsApp

*n8n workflow in In*

- **File:** `n8n_workflows\In\BAT_IN_WhatsApp.json`
- **Tech:** json
- **Webhook:** `whatsapp`
- **Tags:** BattCRM, In

### Out

#### BAT OUT Avito

*n8n workflow in Out*

- **File:** `n8n_workflows\Out\BAT_OUT_Avito.json`
- **Tech:** json
- **Tags:** BattCRM, Out

#### BAT OUT MAX

*n8n workflow in Out*

- **File:** `n8n_workflows\Out\BAT_OUT_MAX.json`
- **Tech:** json
- **Tags:** BattCRM, Out

#### BAT OUT Telegram

*n8n workflow in Out*

- **File:** `n8n_workflows\Out\BAT_OUT_Telegram.json`
- **Tech:** json
- **Tags:** BattCRM, Out

#### BAT OUT VK

*n8n workflow in Out*

- **File:** `n8n_workflows\Out\BAT_OUT_VK.json`
- **Tech:** json
- **Tags:** BattCRM, Out

#### BAT OUT WhatsApp

*n8n workflow in Out*

- **File:** `n8n_workflows\Out\BAT_OUT_WhatsApp.json`
- **Tech:** json
- **Tags:** BattCRM, Out

### TaskWork

#### BAT AI Universal Worker

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 1

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_1.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 2

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_2.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 3

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_3.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 4

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_4.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 5

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_5.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 6

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_6.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT AI Universal Worker 7

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_AI_Universal_Worker_7.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 1

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_1.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 10

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_10.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 2

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_2.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 3

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_3.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 4

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_4.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 5

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_5.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 6

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_6.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 7

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_7.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 8

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_8.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Batch Debouncer 9

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Batch_Debouncer_9.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 1

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_1.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 2

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_2.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 3

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_3.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 4

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_4.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 5

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_5.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Out Processor 6

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Out_Processor_6.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT Queue Processor

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Queue_Processor.json`
- **Tech:** json
- **Tags:** BattCRM, TaskWork

#### BAT_Queue_Processor

*n8n workflow in TaskWork*

- **File:** `n8n_workflows\TaskWork\BAT_Queue_Processor.json`
- **Tech:** json

### Tool

#### Tool - Build Appeal Meta

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\Tool_-_Build_Appeal_Meta.json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### Tool - Извлечь бренд и модель (AI Agent)

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\Tool_-_Извлечь_бренд_и_модель_(AI_Agent).json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### Tool - Определить тип обращения (AI Agent)

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\Tool_-_Определить_тип_обращения_(AI_Agent).json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### Tool - Определить тип ремонта (AI Agent)

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\Tool_-_Определить_тип_ремонта_(AI_Agent).json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### Tool - Определить чья запчасть (AI Agent)

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\Tool_-_Определить_чья_запчасть_(AI_Agent).json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### VektorPrompt

*n8n workflow in Tool*

- **File:** `n8n_workflows\Tool\VektorPrompt.json`
- **Tech:** json
- **Tags:** BattCRM, Tool

### modified

#### API_Android_Appeal_Detail_v2

*n8n workflow in modified*

- **File:** `workflows_to_import\modified\API_Android_Appeal_Detail_v2.json`
- **Tech:** json
- **Webhook:** `api/android/appeals/:id`

#### BAT AI Appeal Router

*n8n workflow in modified*

- **File:** `workflows_to_import\modified\BAT_AI_Appeal_Router_with_Matcher.json`
- **Tech:** json
- **Tags:** BattCRM, Core

#### BAT AI Task Dispatcher

*n8n workflow in modified*

- **File:** `workflows_to_import\modified\BAT_AI_Task_Dispatcher_with_Neo4j.json`
- **Tech:** json
- **Tags:** BattCRM, Tool

#### BAT Neo4j Context Builder

*n8n workflow in modified*

- **File:** `workflows_to_import\modified\BAT_Neo4j_Context_Builder_with_Matcher.json`
- **Tech:** json
- **Webhook:** `neo4j/context`
- **Tags:** BattCRM, Core

---

## Tables (37)

#### ab_test_results

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### ab_tests

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### ai_chat_memory

*Table from 007_new_tables_part5_ai_voice.sql*

- **File:** `database\migrations\007_new_tables_part5_ai_voice.sql`
- **Tech:** sql

#### ai_entity_configs

*Table from 010_universal_ai_worker.sql*

- **File:** `database\migrations\010_universal_ai_worker.sql`
- **Tech:** sql

#### ai_extraction_results

*Table from 010_universal_ai_worker.sql*

- **File:** `database\migrations\010_universal_ai_worker.sql`
- **Tech:** sql

#### ai_extraction_tasks

*Table from 010_universal_ai_worker.sql*

- **File:** `database\migrations\010_universal_ai_worker.sql`
- **Tech:** sql

#### appeal_devices

*Table from 003_new_tables_part1_multi_appeals.sql*

- **File:** `database\migrations\003_new_tables_part1_multi_appeals.sql`
- **Tech:** sql

#### appeal_dialog_state

*Table from 010_universal_ai_worker.sql*

- **File:** `database\migrations\010_universal_ai_worker.sql`
- **Tech:** sql

#### appeal_repairs

*Table from 003_new_tables_part1_multi_appeals.sql*

- **File:** `database\migrations\003_new_tables_part1_multi_appeals.sql`
- **Tech:** sql

#### appeal_stages

*Table from 004_new_tables_part2_funnel.sql*

- **File:** `database\migrations\004_new_tables_part2_funnel.sql`
- **Tech:** sql

#### appeal_stage_transitions

*Table from 004_new_tables_part2_funnel.sql*

- **File:** `database\migrations\004_new_tables_part2_funnel.sql`
- **Tech:** sql

#### archived_data

*Table from 009_new_tables_part7_ttl_policies.sql*

- **File:** `database\migrations\009_new_tables_part7_ttl_policies.sql`
- **Tech:** sql

#### client_contacts

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### client_fingerprints

*Table from 014_fingerprints_and_short_links.sql*

- **File:** `database\migrations\014_fingerprints_and_short_links.sql`
- **Tech:** sql

#### client_merges

*Table from 014_fingerprints_and_short_links.sql*

- **File:** `database\migrations\014_fingerprints_and_short_links.sql`
- **Tech:** sql

#### contacts

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### context_fields_config

*Table from 002_context_fields_management.sql*

- **File:** `database\migrations\002_context_fields_management.sql`
- **Tech:** sql

#### creatives

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### creative_usage_log

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### crm_field_mappings

*Table from 008_new_tables_part6_crm_integrations.sql*

- **File:** `database\migrations\008_new_tables_part6_crm_integrations.sql`
- **Tech:** sql

#### crm_integrations

*Table from 008_new_tables_part6_crm_integrations.sql*

- **File:** `database\migrations\008_new_tables_part6_crm_integrations.sql`
- **Tech:** sql

#### crm_sync_history

*Table from 008_new_tables_part6_crm_integrations.sql*

- **File:** `database\migrations\008_new_tables_part6_crm_integrations.sql`
- **Tech:** sql

#### data_retention_policies

*Table from 009_new_tables_part7_ttl_policies.sql*

- **File:** `database\migrations\009_new_tables_part7_ttl_policies.sql`
- **Tech:** sql

#### enrichment_actions

*Table from 015_contacts_and_enrichment.sql*

- **File:** `database\migrations\015_contacts_and_enrichment.sql`
- **Tech:** sql

#### fingerprints

*Table from 014_fingerprints_and_short_links.sql*

- **File:** `database\migrations\014_fingerprints_and_short_links.sql`
- **Tech:** sql

#### fingerprint_visits

*Table from 014_fingerprints_and_short_links.sql*

- **File:** `database\migrations\014_fingerprints_and_short_links.sql`
- **Tech:** sql

#### issue_types

*Table from 001_step3_create_issue_types.sql*

- **File:** `database\migrations\001_step3_create_issue_types.sql`
- **Tech:** sql

#### price_list_items

*Table from 006_new_tables_part4_pricing.sql*

- **File:** `database\migrations\006_new_tables_part4_pricing.sql`
- **Tech:** sql

#### price_lists

*Table from 006_new_tables_part4_pricing.sql*

- **File:** `database\migrations\006_new_tables_part4_pricing.sql`
- **Tech:** sql

#### price_mappings

*Table from 006_new_tables_part4_pricing.sql*

- **File:** `database\migrations\006_new_tables_part4_pricing.sql`
- **Tech:** sql

#### promo_history

*Table from 005_new_tables_part3_promo.sql*

- **File:** `database\migrations\005_new_tables_part3_promo.sql`
- **Tech:** sql

#### promo_materials

*Table from 005_new_tables_part3_promo.sql*

- **File:** `database\migrations\005_new_tables_part3_promo.sql`
- **Tech:** sql

#### promo_triggers

*Table from 005_new_tables_part3_promo.sql*

- **File:** `database\migrations\005_new_tables_part3_promo.sql`
- **Tech:** sql

#### repair_categories

*Table from 003_new_tables_part1_multi_appeals.sql*

- **File:** `database\migrations\003_new_tables_part1_multi_appeals.sql`
- **Tech:** sql

#### short_links

*Table from 014_fingerprints_and_short_links.sql*

- **File:** `database\migrations\014_fingerprints_and_short_links.sql`
- **Tech:** sql

#### touchpoints

*Table from 017_multicontext_enhancements.sql*

- **File:** `database\migrations\017_multicontext_enhancements.sql`
- **Tech:** sql

#### voice_transcriptions

*Table from 007_new_tables_part5_ai_voice.sql*

- **File:** `database\migrations\007_new_tables_part5_ai_voice.sql`
- **Tech:** sql

---

## Android Activitys (3)

#### AppealDetailActivity

- **File:** `app\src\main\java\com\eldoleado\app\AppealDetailActivity.kt`
- **Tech:** kotlin
- **Extends:** AppCompatActivity

#### LoginActivity

- **File:** `app\src\main\java\com\eldoleado\app\LoginActivity.kt`
- **Tech:** kotlin
- **Extends:** AppCompatActivity

#### MainActivity

- **File:** `app\src\main\java\com\eldoleado\app\MainActivity.kt`
- **Tech:** kotlin
- **Extends:** AppCompatActivity

---

## Android Viewmodels (2)

#### AppealDetailViewModel

*StateFlows: state, isRefreshing*

- **File:** `app\src\main\java\com\eldoleado\app\viewmodel\AppealDetailViewModel.kt`
- **Tech:** kotlin

#### AppealsViewModel

*StateFlows: appeals, isLoading, error*

- **File:** `app\src\main\java\com\eldoleado\app\viewmodel\AppealsViewModel.kt`
- **Tech:** kotlin

---

## Android Repositorys (2)

#### AppealsRepository

- **File:** `app\src\main\java\com\eldoleado\app\data\repository\AppealsRepository.kt`
- **Tech:** kotlin

#### FCMRepository

- **File:** `app\src\main\java\com\eldoleado\app\fcm\FCMRepository.kt`
- **Tech:** kotlin

---

## Mcp Servers (10)

#### MCP_AVITO

*MCP server for AVITO integration*

- **File:** `MCP\mcp-avito`
- **Tech:** python

#### MCP_DOCS-RAG

*MCP server for DOCS-RAG integration*

- **File:** `MCP\mcp-docs-rag`
- **Tech:** python

#### MCP_FORM

*MCP server for FORM integration*

- **File:** `MCP\mcp-form`
- **Tech:** python

#### MCP_INSTAGRAM

*MCP server for INSTAGRAM integration*

- **File:** `MCP\mcp-instagram`
- **Tech:** python

#### MCP_MAX

*MCP server for MAX integration*

- **File:** `MCP\mcp-max`
- **Tech:** python

#### MCP_MAX-WAPPI

*MCP server for MAX-WAPPI integration*

- **File:** `MCP\mcp-max-wappi`
- **Tech:** python

#### MCP_TELEGRAM

*MCP server for TELEGRAM integration*

- **File:** `MCP\mcp-telegram`
- **Tech:** python

#### MCP_VK

*MCP server for VK integration*

- **File:** `MCP\mcp-vk`
- **Tech:** python

#### MCP_VK-WAPPI

*MCP server for VK-WAPPI integration*

- **File:** `MCP\mcp-vk-wappi`
- **Tech:** python

#### MCP_WHATSAPP

*MCP server for WHATSAPP integration*

- **File:** `MCP\mcp-whatsapp`
- **Tech:** python

---

## Scripts (31)

#### auto_update

*Python script*

- **File:** `scripts\auto_update.py`
- **Tech:** python

#### avito_parser

*Python script*

- **File:** `scripts\avito_parser.py`
- **Tech:** python

#### check_workflow_tags

*Python script*

- **File:** `scripts\check_workflow_tags.py`
- **Tech:** python

#### extract_workflows_info

*Python script*

- **File:** `scripts\extract_workflows_info.py`
- **Tech:** python

#### full_sync

*Python script*

- **File:** `scripts\full_sync.py`
- **Tech:** python

#### generate_all_flow_docs

*Python script*

- **File:** `scripts\generate_all_flow_docs.py`
- **Tech:** python

#### generate_flow_doc

*Python script*

- **File:** `scripts\generate_flow_doc.py`
- **Tech:** python

#### generate_knowledge_base

*Python script*

- **File:** `scripts\generate_knowledge_base.py`
- **Tech:** python

#### git-sync

*Bash script*

- **File:** `scripts\git-sync.sh`
- **Tech:** bash

#### n8n_manager

*Python script*

- **File:** `scripts\n8n_manager.py`
- **Tech:** python

#### populate_knowledge_base

*Python script*

- **File:** `scripts\populate_knowledge_base.py`
- **Tech:** python

#### populate_node_connections

*Python script*

- **File:** `scripts\populate_node_connections.py`
- **Tech:** python

#### populate_relations

*Python script*

- **File:** `scripts\populate_relations.py`
- **Tech:** python

#### populate_workflow_nodes

*Python script*

- **File:** `scripts\populate_workflow_nodes.py`
- **Tech:** python

#### prepare_workflows_section

*Python script*

- **File:** `scripts\prepare_workflows_section.py`
- **Tech:** python

#### run_migration

*Python script*

- **File:** `scripts\run_migration.py`
- **Tech:** python

#### run_migration_via_api

*Python script*

- **File:** `scripts\run_migration_via_api.py`
- **Tech:** python

#### setup_dev_environment

*Python script*

- **File:** `scripts\setup_dev_environment.py`
- **Tech:** python

#### supabase_manager

*Python script*

- **File:** `scripts\supabase_manager.py`
- **Tech:** python

#### sync_and_update_kb

*Python script*

- **File:** `scripts\sync_and_update_kb.py`
- **Tech:** python

#### sync_kb

*Batch script*

- **File:** `scripts\sync_kb.bat`
- **Tech:** batch

#### sync_n8n_workflows

*Python script*

- **File:** `scripts\sync_n8n_workflows.py`
- **Tech:** python

#### test_connections

*Python script*

- **File:** `scripts\test_connections.py`
- **Tech:** python

#### trace_flow

*Python script*

- **File:** `scripts\trace_flow.py`
- **Tech:** python

#### update_flow_docs

*Python script*

- **File:** `scripts\update_flow_docs.py`
- **Tech:** python

#### update_notifier

*Javascript script*

- **File:** `scripts\update_notifier.js`
- **Tech:** javascript

#### update_workflow

*Python script*

- **File:** `scripts\update_workflow.py`
- **Tech:** python

#### update_workflow_auto

*Python script*

- **File:** `scripts\update_workflow_auto.py`
- **Tech:** python

#### upload_appeal_detail

*Javascript script*

- **File:** `scripts\upload_appeal_detail.js`
- **Tech:** javascript

#### UPLOAD_TO_GITHUB

*Batch script*

- **File:** `scripts\UPLOAD_TO_GITHUB.bat`
- **Tech:** batch

#### webhook_sync_handler

*Python script*

- **File:** `scripts\webhook_sync_handler.py`
- **Tech:** python

---

## Docs (105)

### Android_Changes_To_Apply

#### APPLY_CHANGES

*Скрипт применения изменений*

- **File:** `Plans\Android_Changes_To_Apply\APPLY_CHANGES.md`
- **Tech:** markdown

#### README

*Изменения Android приложения для применения*

- **File:** `Plans\Android_Changes_To_Apply\README.md`
- **Tech:** markdown

### Plans

#### Eldoleado full

*📊 Полный анализ проекта BatteryCRM*

- **File:** `Plans\Eldoleado full.md`
- **Tech:** markdown

#### Eldoleado_Данные_Каналов

*Eldoleado: Полный справочник каналов и данных клиентов*

- **File:** `Plans\Eldoleado_Данные_Каналов.md`
- **Tech:** markdown

#### Eldoleado_Мультичат_ТЗ_v2

*Техническое задание: Мультиконтекстный диалоговый движок Eldoleado*

- **File:** `Plans\Eldoleado_Мультичат_ТЗ_v2.md`
- **Tech:** markdown

#### Eldoleado_Спецификация_Графа

*Eldoleado: Спецификация графовой архитектуры*

- **File:** `Plans\Eldoleado_Спецификация_Графа.md`
- **Tech:** markdown

#### MCP_Процесс_Разработки

*Разработка MCP — Workflow с AI-ассистентом*

- **File:** `Plans\MCP_Процесс_Разработки.md`
- **Tech:** markdown

#### MVP_Стратегия_Интеграции

*MVP — Стратегия интеграций и быстрого запуска*

- **File:** `Plans\MVP_Стратегия_Интеграции.md`
- **Tech:** markdown

#### TZ-VPN-Infrastructure

*Техническое задание*

- **File:** `Plans\TZ-VPN-Infrastructure.md`
- **Tech:** markdown

#### Админка_Спецификация

*Eldoleado Admin Panel — Спецификация*

- **File:** `Plans\Админка_Спецификация.md`
- **Tech:** markdown

#### Архитектура_AI_Системы

*AI Система Eldoleado — Архитектура и Workflow*

- **File:** `Plans\Архитектура_AI_Системы.md`
- **Tech:** markdown

#### База знаний по ремонту техники — iFixit Full Parser

*База знаний по ремонту техники — iFixit Full Parser*

- **File:** `Plans\База знаний по ремонту техники — iFixit Full Parser.md`
- **Tech:** markdown

#### Блочная_Архитектура

*Архитектура взаимодействия блоков — Концепция*

- **File:** `Plans\Блочная_Архитектура.md`
- **Tech:** markdown

#### Вертикаль_Автолид

*Вертикаль "Автоклиент" — Универсальная система продаж*

- **File:** `Plans\Вертикаль_Автолид.md`
- **Tech:** markdown

#### Видение_AI_Конструктора

*AI Конструктор Вертикалей — Концепция*

- **File:** `Plans\Видение_AI_Конструктора.md`
- **Tech:** markdown

#### Диалог_Анна_Пирожки

*Диалог: Анна (пироги на заказ) — AI Конструктор*

- **File:** `Plans\Диалог_Анна_Пирожки.md`
- **Tech:** markdown

#### Идеи

*Ideas - Идеи развития BatteryCRM после MVP*

- **File:** `Plans\Идеи.md`
- **Tech:** markdown

#### Инструкция VPN

*Инструкция по подключению к VPN*

- **File:** `Plans\Инструкция VPN.md`
- **Tech:** markdown

#### Масштаб_и_стабильность

*ТЗ: Масштабирование и отказоустойчивость Eldoleado*

- **File:** `Plans\Масштаб_и_стабильность.md`
- **Tech:** markdown

#### Миграция_на_Python_Архитектура

*BattCRM - Архитектура миграции на Python*

- **File:** `Plans\Миграция_на_Python_Архитектура.md`
- **Tech:** markdown

#### НАВИГАЦИЯ_ПРОЕКТА

*Eldoleado — Навигационный Граф Проекта*

- **File:** `Plans\НАВИГАЦИЯ_ПРОЕКТА.md`
- **Tech:** markdown

#### Настройка стабильности

*Настройка отказоустойчивости MCP серверов*

- **File:** `Plans\Настройка стабильности.md`
- **Tech:** markdown

#### План_Мультиустройства_Ремонты

*План: Структура Клиент → Аппарат(ы) → Неисправность(и)*

- **File:** `Plans\План_Мультиустройства_Ремонты.md`
- **Tech:** markdown

#### План_Остановки

*STOP Plan - Итоги сессии планирования 2025-11-26*

- **File:** `Plans\План_Остановки.md`
- **Tech:** markdown

#### План_Режим_Работы_Обращения

*План реализации: operation_mode для каждой заявки*

- **File:** `Plans\План_Режим_Работы_Обращения.md`
- **Tech:** markdown

#### Стратегия_Маркетологов

*Стратегия работы с маркетологами — От пользователя до владельца вертикали*

- **File:** `Plans\Стратегия_Маркетологов.md`
- **Tech:** markdown

#### Стратегия_Роста

*Стратегия роста — Два продукта + Владельцы вертикалей*

- **File:** `Plans\Стратегия_Роста.md`
- **Tech:** markdown

#### Тарифы_Ценообразование

*Тарифные планы Eldoleado — Концепция*

- **File:** `Plans\Тарифы_Ценообразование.md`
- **Tech:** markdown

### ai

#### AI_MULTI_ENTITY_EXTRACTOR_DEPLOYMENT

*Деплой AI Multi-Entity Extractor - Пошаговая инструкция*

- **File:** `docs\ai\AI_MULTI_ENTITY_EXTRACTOR_DEPLOYMENT.md`
- **Tech:** markdown

#### AI_MULTI_ENTITY_EXTRACTOR_DOC

*AI Multi-Entity Extractor - Документация*

- **File:** `docs\ai\AI_MULTI_ENTITY_EXTRACTOR_DOC.md`
- **Tech:** markdown

#### UNIVERSAL_AI_PROMPT_SYSTEM

*Universal AI Prompt System - Архитектура*

- **File:** `docs\ai\UNIVERSAL_AI_PROMPT_SYSTEM.md`
- **Tech:** markdown

### android

#### Multi-Device Session Management - Frontend Implementation Summary

*Multi-Device Session Management - Frontend Implementation Summary*

- **File:** `docs\android\Multi-Device Session Management - Frontend Implementation Summary.md`
- **Tech:** markdown

#### TZ_Android_FCM_Integration

*ТЗ-2: Android App - Интеграция Firebase FCM*

- **File:** `docs\android\TZ_Android_FCM_Integration.md`
- **Tech:** markdown

### architecture

#### HORIZONTAL_VERTICAL_ARCHITECTURE_V2

*Horizontal/Vertical Platform Architecture v2.0*

- **File:** `docs\architecture\HORIZONTAL_VERTICAL_ARCHITECTURE_V2.md`
- **Tech:** markdown

#### MIGRATION_PLAN_H_V_SPLIT

*План миграции: Монолит → Horizontal/Vertical Architecture*

- **File:** `docs\architecture\MIGRATION_PLAN_H_V_SPLIT.md`
- **Tech:** markdown

### backend

#### Backend Implementation Checklist - Multi-Device Session Management

*Backend Implementation Checklist - Multi-Device Session Management*

- **File:** `docs\backend\Backend Implementation Checklist - Multi-Device Session Management.md`
- **Tech:** markdown

#### Backend Parameter Meta Specification

- **File:** `docs\backend\Backend Parameter Meta Specification.md`
- **Tech:** markdown

#### Database_Structure_BatteryCRM_COMPLETE

*Database Structure (BatteryCRM) - COMPLETE*

- **File:** `docs\backend\Database_Structure_BatteryCRM_COMPLETE.md`
- **Tech:** markdown

#### FCM_BACKEND_SYNC

*Синхронизация Android ↔ Backend по FCM*

- **File:** `docs\backend\FCM_BACKEND_SYNC.md`
- **Tech:** markdown

#### SESSION_HANDOFF_MULTI_DEVICE_COMPLETE

*Session Handoff - Multi-Device Session Management Implementation*

- **File:** `docs\backend\SESSION_HANDOFF_MULTI_DEVICE_COMPLETE.md`
- **Tech:** markdown

### current

#### 01_SYSTEM_OVERVIEW

*BatteryCRM - Техническое задание: Обзор системы*

- **File:** `docs\current\01_SYSTEM_OVERVIEW.md`
- **Tech:** markdown

#### 02_DATABASE_SCHEMA_EXTENDED

*BatteryCRM - База данных: Расширенная схема*

- **File:** `docs\current\02_DATABASE_SCHEMA_EXTENDED.md`
- **Tech:** markdown

#### 03_WORKFLOWS_COMPLETE

*BatteryCRM - n8n Workflows: Полная спецификация*

- **File:** `docs\current\03_WORKFLOWS_COMPLETE.md`
- **Tech:** markdown

#### 04_API_SPECIFICATION

*BatteryCRM - REST API Specification*

- **File:** `docs\current\04_API_SPECIFICATION.md`
- **Tech:** markdown

#### 05_ANDROID_APP_SPEC

*BatteryCRM - Android App: Техническая спецификация*

- **File:** `docs\current\05_ANDROID_APP_SPEC.md`
- **Tech:** markdown

#### 06_DESKTOP_APP_SPEC

*BatteryCRM - Desktop App: Техническая спецификация*

- **File:** `docs\current\06_DESKTOP_APP_SPEC.md`
- **Tech:** markdown

#### 07_INTEGRATIONS

*BatteryCRM - Интеграции: Техническая спецификация*

- **File:** `docs\current\07_INTEGRATIONS.md`
- **Tech:** markdown

#### 08_DEPLOYMENT_GUIDE

*BatteryCRM - Deployment Guide: Руководство по развёртыванию*

- **File:** `docs\current\08_DEPLOYMENT_GUIDE.md`
- **Tech:** markdown

#### TZ_vertical_AI_v3

*ТЗ ДЛЯ ИИ-РАЗРАБОТЧИКА ВЕРТИКАЛИ “РЕМОНТ ТЕЛЕФОНОВ”*

- **File:** `docs\current\TZ_vertical_AI_v3.md`
- **Tech:** markdown

### debug

#### redis-debug-guide

*Руководство по отладке Redis в n8n workflows*

- **File:** `docs\debug\redis-debug-guide.md`
- **Tech:** markdown

#### start

*Отладка: Telegram → AI не работает*

- **File:** `docs\debug\start.md`
- **Tech:** markdown

#### stop

*Отладка: Redis PUSH не работает в n8n*

- **File:** `docs\debug\stop.md`
- **Tech:** markdown

### deployment

#### DEPLOYMENT_CHECKLIST

*🚀 Deployment Checklist - AI Multi-Entity Extractor*

- **File:** `docs\deployment\DEPLOYMENT_CHECKLIST.md`
- **Tech:** markdown

#### DEPLOYMENT_CHECKLIST_OPENAI

*🚀 Deployment Checklist - AI Multi-Entity Extractor (OpenAI)*

- **File:** `docs\deployment\DEPLOYMENT_CHECKLIST_OPENAI.md`
- **Tech:** markdown

#### DEPLOY_NOW

*🚀 DEPLOY NOW - AI Multi-Entity Extractor (OpenAI)*

- **File:** `docs\deployment\DEPLOY_NOW.md`
- **Tech:** markdown

### docs

#### Firebase_Setup

*Настройка Firebase для Eldoleado*

- **File:** `docs\Firebase_Setup.md`
- **Tech:** markdown

#### N8N_RULES

*ПРАВИЛА РАБОТЫ С n8n (ВАЖНО!)*

- **File:** `docs\N8N_RULES.md`
- **Tech:** markdown

#### Plan_Multi_Channel_Telegram_Avito

*План параллельного подключения каналов: Telegram + Avito*

- **File:** `docs\Plan_Multi_Channel_Telegram_Avito.md`
- **Tech:** markdown

#### TZ_Avito_MCP_Integration

*ТЗ: Интеграция Avito через MCP REST API Server*

- **File:** `docs\TZ_Avito_MCP_Integration.md`
- **Tech:** markdown

### features

#### ENRICHMENT_SYSTEM

*Система Enrichment (Обогащения контактов)*

- **File:** `docs\features\ENRICHMENT_SYSTEM.md`
- **Tech:** markdown

#### MULTI_APPEAL_API_DOCUMENTATION

*Multi-Appeal API Documentation*

- **File:** `docs\features\MULTI_APPEAL_API_DOCUMENTATION.md`
- **Tech:** markdown

#### MULTI_APPEAL_DEPLOYMENT

*Multi-Appeal API Deployment Guide*

- **File:** `docs\features\MULTI_APPEAL_DEPLOYMENT.md`
- **Tech:** markdown

### flows

#### action_appeal_detail

*Действие: Детали обращения*

- **File:** `docs\flows\action_appeal_detail.md`
- **Tech:** markdown

#### action_appeals_list

*Действие: Список обращений*

- **File:** `docs\flows\action_appeals_list.md`
- **Tech:** markdown

#### action_device_create

*Действие: Добавить устройство*

- **File:** `docs\flows\action_device_create.md`
- **Tech:** markdown

#### action_device_delete

*Действие: Удалить устройство*

- **File:** `docs\flows\action_device_delete.md`
- **Tech:** markdown

#### action_device_update

*Действие: Обновить устройство*

- **File:** `docs\flows\action_device_update.md`
- **Tech:** markdown

#### action_login

*Действие: Авторизация оператора*

- **File:** `docs\flows\action_login.md`
- **Tech:** markdown

#### action_logout

*Действие: Выход из системы*

- **File:** `docs\flows\action_logout.md`
- **Tech:** markdown

#### action_normalize

*Действие: Нормализация текста ответа*

- **File:** `docs\flows\action_normalize.md`
- **Tech:** markdown

#### action_reject

*Действие: Отклонить обращение*

- **File:** `docs\flows\action_reject.md`
- **Tech:** markdown

#### action_repair_create

*Действие: Добавить ремонт*

- **File:** `docs\flows\action_repair_create.md`
- **Tech:** markdown

#### action_repair_delete

*Действие: Удалить ремонт*

- **File:** `docs\flows\action_repair_delete.md`
- **Tech:** markdown

#### action_repair_update

*Действие: Обновить ремонт*

- **File:** `docs\flows\action_repair_update.md`
- **Tech:** markdown

#### action_send_promo

*Действие: Отправить промо-материал*

- **File:** `docs\flows\action_send_promo.md`
- **Tech:** markdown

#### action_send_response

*Действие: Отправка ответа клиенту*

- **File:** `docs\flows\action_send_response.md`
- **Tech:** markdown

#### action_take_appeal

*Действие: Взять обращение в работу*

- **File:** `docs\flows\action_take_appeal.md`
- **Tech:** markdown

#### channel_avito

*Поток: Входящее сообщение из Avito*

- **File:** `docs\flows\channel_avito.md`
- **Tech:** markdown

#### channel_form

*Поток: Заявка с формы на сайте*

- **File:** `docs\flows\channel_form.md`
- **Tech:** markdown

#### channel_max

*Поток: Входящее сообщение из MAX.ru*

- **File:** `docs\flows\channel_max.md`
- **Tech:** markdown

#### channel_phone

*Поток: Входящий звонок*

- **File:** `docs\flows\channel_phone.md`
- **Tech:** markdown

#### channel_telegram

*Поток: Входящее сообщение из Telegram*

- **File:** `docs\flows\channel_telegram.md`
- **Tech:** markdown

#### channel_vk

*Поток: Входящее сообщение из VK*

- **File:** `docs\flows\channel_vk.md`
- **Tech:** markdown

#### channel_whatsapp

*Поток: Входящее сообщение из WhatsApp*

- **File:** `docs\flows\channel_whatsapp.md`
- **Tech:** markdown

### n8n

#### HOW_TO_UPDATE_BAT_AI_APPEAL_ROUTER_REAL

*Инструкция по обновлению AI воркера для управления стадиями*

- **File:** `docs\n8n\HOW_TO_UPDATE_BAT_AI_APPEAL_ROUTER_REAL.md`
- **Tech:** markdown

#### N8N_VERSION_INFO

*⚠️ ВАЖНО: Версия n8n*

- **File:** `docs\n8n\N8N_VERSION_INFO.md`
- **Tech:** markdown

#### README_N8N_MANAGER

*🛠️ n8n Manager - Инструкция по использованию*

- **File:** `docs\n8n\README_N8N_MANAGER.md`
- **Tech:** markdown

#### README_SUPABASE_MANAGER

*🗄️ Supabase Manager - Инструкция по использованию*

- **File:** `docs\n8n\README_SUPABASE_MANAGER.md`
- **Tech:** markdown

### prompts

#### multi_entity_extractor_prompt

*AI Multi-Entity Extractor - System Prompt*

- **File:** `docs\ai\prompts\multi_entity_extractor_prompt.md`
- **Tech:** markdown

### setup

#### GITHUB_SETUP_INSTRUCTIONS

*📦 Инструкция: Загрузка проекта на GitHub*

- **File:** `docs\setup\GITHUB_SETUP_INSTRUCTIONS.md`
- **Tech:** markdown

#### GITHUB_SYNC_SETUP

*Настройка синхронизации с GitHub*

- **File:** `docs\setup\GITHUB_SYNC_SETUP.md`
- **Tech:** markdown

#### MCP_ANDROID_SETUP

*MCP Setup для Android-разработки*

- **File:** `docs\setup\MCP_ANDROID_SETUP.md`
- **Tech:** markdown

#### MCP_COMPLETE_SETUP

*🎉 MCP Setup Complete - Полная конфигурация*

- **File:** `docs\setup\MCP_COMPLETE_SETUP.md`
- **Tech:** markdown

#### MCP_POSTGRES_CONFIG

*Настройка MCP PostgreSQL Server*

- **File:** `docs\setup\MCP_POSTGRES_CONFIG.md`
- **Tech:** markdown

#### MCP_POSTGRES_SETUP

*MCP Setup для PostgreSQL/Supabase*

- **File:** `docs\setup\MCP_POSTGRES_SETUP.md`
- **Tech:** markdown

#### MCP_STATUS_CHECK

*Статус проверки MCP серверов*

- **File:** `docs\setup\MCP_STATUS_CHECK.md`
- **Tech:** markdown

#### QUICK_REFERENCE

*📚 Quick Reference - Шпаргалка команд BatteryCRM*

- **File:** `docs\setup\QUICK_REFERENCE.md`
- **Tech:** markdown

#### QUICK_START_N8N_UPDATE

*🚀 Быстрый старт: Обновление BAT AI Appeal Router в n8n UI*

- **File:** `docs\setup\QUICK_START_N8N_UPDATE.md`
- **Tech:** markdown

#### README_ENVIRONMENT_SETUP

*🎉 BatteryCRM - Автоматическая настройка среды разработки*

- **File:** `docs\setup\README_ENVIRONMENT_SETUP.md`
- **Tech:** markdown

#### README_GITHUB

*🔋 BatteryCRM*

- **File:** `docs\setup\README_GITHUB.md`
- **Tech:** markdown

#### SETUP_COMPLETE

*✅ Настройка завершена!*

- **File:** `docs\setup\SETUP_COMPLETE.md`
- **Tech:** markdown

#### SETUP_COMPLETE_SUPABASE

*✅ Version Control для Supabase - Готово!*

- **File:** `docs\setup\SETUP_COMPLETE_SUPABASE.md`
- **Tech:** markdown

#### SETUP_NEW_MACHINE

*🚀 Настройка среды разработки BatteryCRM на новом компьютере*

- **File:** `docs\setup\SETUP_NEW_MACHINE.md`
- **Tech:** markdown

### specs

#### message_processing_logic

*Логика обработки сообщений*

- **File:** `docs\specs\message_processing_logic.md`
- **Tech:** markdown

#### tenant_ai_instructions

*ТЗ: Tenant AI Instructions (Обучаемые реакции бота)*

- **File:** `docs\specs\tenant_ai_instructions.md`
- **Tech:** markdown

---

## Android Adapters (3)

- **AppealsAdapter** - None
- **DevicesAdapter** - None
- **MessagesAdapter** - None

---

## Android Apis (3)

- **AuthInterceptor** - None
- **ErrorInterceptor** - None
- **RetrofitClient** - None

---

## Android Api Services (1)

- **ApiService** - None

---

## Android Classs (16)

- **AppealUpdateEvent** - None
- **EldoleadoApplication** - None
- **SessionManager** - None
- **BootReceiver** - None
- **CallReceiver** - None
- **CallRecordingPreferences** - None
- **CallRecordingService** - None
- **CallUploadWorker** - None
- **Message** - None
- **AppDatabase** - None
- **RecordingTileService** - None
- **RootChecker** - None
- **RootRecordingPreferences** - None
- **StereoCallRecorder** - None
- **DataCleanupWorker** - None
- **AppealInfoBottomSheet** - None

---

## Android Daos (2)

- **AppealDao** - None
- **MessageDao** - None

---

## Android Entitys (2)

- **AppealEntity** - None
- **MessageEntity** - None

---

## Android Fcms (1)

- **EldoleadoMessagingService** - None

---

## Component Relations

### Calls (77)

- `workflow:API_Android_Appeal_Detail` → `workflow:Tool - Build Appeal Meta`
- `workflow:API_Android_Appeal_Detail_v2` → `workflow:Tool - Build Appeal Meta`
- `workflow:API_Android_Normalize` → `workflow:BAT Operator Response Handler 3 - Text Voice Normalize`
- `workflow:API_Operator_Appeal_Detail` → `workflow:Tool - Build Appeal Meta`
- `workflow:API_Operator_Normalize` → `workflow:BAT Operator Response Handler 3 - Text Voice Normalize`
- `android_api_service:ApiService` → `workflow:API_Android_Send_Response`
- `android_api_service:ApiService` → `workflow:API_Android_Appeals_List`
- `android_api_service:ApiService` → `workflow:API_Operator_Appeals_List`
- `android_api_service:ApiService` → `workflow:API_Android_Send_Promo`
- `android_api_service:ApiService` → `workflow:API_Operator_Send_Response`
- `workflow:BAT AI Appeal Router` → `workflow:BAT AI Task Dispatcher`
- `workflow:BAT AI Appeal Router` → `workflow:BAT Neo4j Touchpoint Register`
- `workflow:BAT AI Appeal Router` → `workflow:BAT Operator Notifier`
- `workflow:BAT AI Appeal Router` → `workflow:BAT Neo4j Context Builder`
- `workflow:BAT_AI_Appeal_Router` → `workflow:BAT Neo4j Context Builder`
- `workflow:BAT_AI_Appeal_Router` → `workflow:BAT Neo4j Touchpoint Register`
- `workflow:BAT_AI_Appeal_Router` → `workflow:BAT AI Task Dispatcher`
- `workflow:BAT_AI_Appeal_Router` → `workflow:BAT Operator Notifier`
- `workflow:BAT AI Task Dispatcher` → `workflow:BAT Neo4j Context Builder`
- `workflow:BAT Appeal Manager` → `workflow:BAT Neo4j Touchpoint Register`
- `workflow:BAT Batch Debouncer` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 1` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 10` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 2` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 3` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 4` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 5` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 6` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 7` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 8` → `workflow:BAT Client Resolver`
- `workflow:BAT Batch Debouncer 9` → `workflow:BAT Client Resolver`
- `workflow:BAT Client Response Sender` → `workflow:BAT Operator Response Handler 1 - Main Router`
- `workflow:BAT Client Response Sender` → `workflow:BAT OUT Telegram`
- `workflow:BAT IN Avito` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN Form` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN MAX` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN Phone` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN Telegram` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT_IN_Telegram` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN VK` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT IN WhatsApp` → `workflow:BAT_Tenant_Resolver`
- `workflow:BAT Operator Notifier` → `workflow:BAT_FCM_Sender`
- `workflow:BAT Operator Response Handler 1 - Main Router` → `workflow:BAT Operator Response Handler 3 - Text Voice Normalize`
- `workflow:BAT Operator Response Handler 1 - Main Router` → `workflow:BAT Client Response Sender`
- `workflow:BAT OUT Avito` → `workflow:BAT Neo4j Touchpoint Register`
- `workflow:BAT OUT MAX` → `workflow:BAT Neo4j Touchpoint Register`
- `workflow:BAT Out Processor 1` → `workflow:BAT OUT Avito`
- `workflow:BAT Out Processor 1` → `workflow:BAT OUT WhatsApp`
- `workflow:BAT Out Processor 1` → `workflow:BAT OUT MAX`
- `workflow:BAT Out Processor 1` → `workflow:BAT OUT VK`
- *...and 27 more*

### Depends On (5)

- `android_viewmodel:AppealDetailViewModel` → `android_repository:AppealsRepository`
- `android_viewmodel:AppealsViewModel` → `android_repository:AppealsRepository`
- `android_class:EldoleadoApplication` → `android_repository:AppealsRepository`
- `android_fcm:EldoleadoMessagingService` → `android_repository:FCMRepository`
- `android_activity:LoginActivity` → `android_repository:FCMRepository`

### Documents (208)

- `doc:01_SYSTEM_OVERVIEW` → `table:appeal_devices`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:repair_categories`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:data_retention_policies`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:appeal_devices`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:price_mappings`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:appeal_stages`
- `doc:02_DATABASE_SCHEMA_EXTENDED` → `table:appeal_repairs`
- `doc:03_WORKFLOWS_COMPLETE` → `table:price_mappings`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT OUT Telegram`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT IN Telegram`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT OUT Avito`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT IN WhatsApp`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT OUT VK`
- `doc:03_WORKFLOWS_COMPLETE` → `table:appeal_devices`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT OUT WhatsApp`
- `doc:03_WORKFLOWS_COMPLETE` → `table:archived_data`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT IN VK`
- `doc:03_WORKFLOWS_COMPLETE` → `table:data_retention_policies`
- `doc:03_WORKFLOWS_COMPLETE` → `table:promo_triggers`
- `doc:03_WORKFLOWS_COMPLETE` → `workflow:BAT IN Avito`
- `doc:05_ANDROID_APP_SPEC` → `android_viewmodel:AppealsViewModel`
- `doc:05_ANDROID_APP_SPEC` → `android_activity:MainActivity`
- `doc:05_ANDROID_APP_SPEC` → `android_repository:AppealsRepository`
- `doc:07_INTEGRATIONS` → `workflow:BAT OUT WhatsApp`
- `doc:07_INTEGRATIONS` → `workflow:BAT IN WhatsApp`
- `doc:07_INTEGRATIONS` → `table:crm_sync_history`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT OUT Telegram`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT OUT Avito`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT IN WhatsApp`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT Universal Batcher`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT OUT VK`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT IN Avito`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT Message Router`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT OUT WhatsApp`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT IN VK`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT IN Telegram`
- `doc:08_DEPLOYMENT_GUIDE` → `workflow:BAT_FCM_Sender`
- `doc:action_appeal_detail` → `workflow:API_Android_Appeal_Detail`
- `doc:action_appeal_detail` → `table:appeal_devices`
- `doc:action_appeals_list` → `workflow:API_Android_Appeals_List`
- `doc:action_device_create` → `table:appeal_devices`
- `doc:action_device_create` → `workflow:API_Android_Device_Create`
- `doc:action_device_delete` → `workflow:API_Android_Device_Delete`
- `doc:action_device_delete` → `table:appeal_devices`
- `doc:action_device_update` → `workflow:API_Android_Device_Update`
- `doc:action_device_update` → `table:appeal_devices`
- `doc:action_login` → `workflow:API_Android_Auth`
- `doc:action_logout` → `workflow:API_Android_Logout`
- `doc:action_normalize` → `workflow:API_Android_Normalize`
- `doc:action_reject` → `workflow:API_Android_Reject`
- *...and 158 more*

### Reads From (43)

- `workflow:API_Android_Appeal_Detail` → `table:appeal_repairs`
- `workflow:API_Android_Appeal_Detail` → `table:issue_types`
- `workflow:API_Android_Appeal_Detail` → `table:appeal_devices`
- `workflow:API_Android_Appeal_Detail` → `table:repair_categories`
- `workflow:API_Android_Device_Create` → `table:appeal_devices`
- `workflow:API_Android_Device_Delete` → `table:appeal_devices`
- `workflow:API_Android_Device_Update` → `table:appeal_devices`
- `workflow:API_Android_Manage_Devices` → `table:appeal_devices`
- `workflow:API_Android_Manage_Repairs` → `table:appeal_devices`
- `workflow:API_Android_Manage_Repairs` → `table:appeal_repairs`
- `workflow:API_Android_Repair_Create` → `table:appeal_repairs`
- `workflow:API_Android_Repair_Create` → `table:appeal_devices`
- `workflow:API_Android_Repair_Delete` → `table:appeal_repairs`
- `workflow:API_Android_Repair_Update` → `table:appeal_repairs`
- `workflow:BAT AI Appeal Router` → `table:appeal_devices`
- `workflow:BAT AI Appeal Router` → `table:repair_categories`
- `workflow:BAT AI Appeal Router` → `table:appeal_repairs`
- `workflow:BAT AI Appeal Router` → `table:context_fields_config`
- `workflow:BAT_AI_Appeal_Router` → `table:appeal_repairs`
- `workflow:BAT_AI_Appeal_Router` → `table:repair_categories`
- `workflow:BAT_AI_Appeal_Router` → `table:context_fields_config`
- `workflow:BAT_AI_Appeal_Router` → `table:appeal_devices`
- `workflow:BAT AI Task Dispatcher` → `table:ai_entity_configs`
- `workflow:BAT AI Task Dispatcher` → `table:ai_extraction_tasks`
- `workflow:BAT AI Task Dispatcher` → `table:appeal_devices`
- `workflow:BAT AI Task Dispatcher` → `table:repair_categories`
- `workflow:BAT AI Task Dispatcher` → `table:appeal_repairs`
- `workflow:BAT AI Universal Worker` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 1` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 2` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 3` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 4` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 5` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 6` → `table:ai_extraction_tasks`
- `workflow:BAT AI Universal Worker 7` → `table:ai_extraction_tasks`
- `workflow:BAT Client Resolver` → `table:client_merges`
- `workflow:BAT Disambiguation Handler` → `table:appeal_devices`
- `workflow:BAT Fingerprint Tracker` → `table:fingerprint_visits`
- `workflow:BAT Fingerprint Tracker` → `table:client_fingerprints`
- `workflow:BAT Fingerprint Tracker` → `table:short_links`
- `workflow:BAT Fingerprint Tracker` → `table:fingerprints`
- `workflow:BAT Neo4j Touchpoint Register` → `table:touchpoints`
- `workflow:BAT Short Link Manager` → `table:short_links`

### References (28)

- `table:ab_test_results` → `table:creatives`
- `table:ab_test_results` → `table:ab_tests`
- `table:ab_test_results` → `table:fingerprints`
- `table:ai_extraction_results` → `table:ai_extraction_tasks`
- `table:ai_extraction_tasks` → `table:ai_entity_configs`
- `table:appeal_repairs` → `table:repair_categories`
- `table:appeal_repairs` → `table:appeal_devices`
- `table:appeal_repairs` → `table:issue_types`
- `table:appeal_stage_transitions` → `table:appeal_stages`
- `table:client_contacts` → `table:contacts`
- `table:client_fingerprints` → `table:fingerprints`
- `table:creative_usage_log` → `table:contacts`
- `table:creative_usage_log` → `table:creatives`
- `table:crm_field_mappings` → `table:crm_integrations`
- `table:crm_sync_history` → `table:crm_integrations`
- `table:enrichment_actions` → `table:contacts`
- `table:fingerprint_visits` → `table:fingerprints`
- `table:fingerprint_visits` → `table:short_links`
- `table:issue_types` → `table:repair_categories`
- `table:price_list_items` → `table:price_lists`
- `table:price_mappings` → `table:price_list_items`
- `table:price_mappings` → `table:repair_categories`
- `table:price_mappings` → `table:issue_types`
- `table:promo_history` → `table:promo_materials`
- `table:promo_history` → `table:promo_triggers`
- `table:promo_triggers` → `table:promo_materials`
- `table:touchpoints` → `table:appeal_repairs`
- `table:touchpoints` → `table:appeal_devices`

### Uses (89)

- `android_api_service:ApiService` → `android_class:Message`
- `android_class:AppDatabase` → `android_entity:MessageEntity`
- `android_class:AppDatabase` → `android_entity:AppealEntity`
- `android_class:AppDatabase` → `android_dao:MessageDao`
- `android_class:AppDatabase` → `android_dao:AppealDao`
- `android_dao:AppealDao` → `android_entity:AppealEntity`
- `android_activity:AppealDetailActivity` → `android_api:RetrofitClient`
- `android_activity:AppealDetailActivity` → `android_class:Message`
- `android_activity:AppealDetailActivity` → `android_class:AppealInfoBottomSheet`
- `android_activity:AppealDetailActivity` → `android_viewmodel:AppealDetailViewModel`
- `android_activity:AppealDetailActivity` → `android_entity:AppealEntity`
- `android_activity:AppealDetailActivity` → `android_entity:MessageEntity`
- `android_activity:AppealDetailActivity` → `android_activity:MainActivity`
- `android_activity:AppealDetailActivity` → `android_class:SessionManager`
- `android_activity:AppealDetailActivity` → `android_adapter:DevicesAdapter`
- `android_activity:AppealDetailActivity` → `android_class:AppealUpdateEvent`
- `android_activity:AppealDetailActivity` → `android_class:EldoleadoApplication`
- `android_activity:AppealDetailActivity` → `android_adapter:MessagesAdapter`
- `android_viewmodel:AppealDetailViewModel` → `android_entity:AppealEntity`
- `android_viewmodel:AppealDetailViewModel` → `android_class:Message`
- `android_viewmodel:AppealDetailViewModel` → `android_entity:MessageEntity`
- `android_class:AppealInfoBottomSheet` → `android_entity:AppealEntity`
- `android_adapter:AppealsAdapter` → `android_entity:AppealEntity`
- `android_repository:AppealsRepository` → `android_dao:MessageDao`
- `android_repository:AppealsRepository` → `android_dao:AppealDao`
- `android_repository:AppealsRepository` → `android_entity:MessageEntity`
- `android_repository:AppealsRepository` → `android_class:Message`
- `android_repository:AppealsRepository` → `android_api_service:ApiService`
- `android_repository:AppealsRepository` → `android_entity:AppealEntity`
- `android_viewmodel:AppealsViewModel` → `android_entity:AppealEntity`
- `android_viewmodel:AppealsViewModel` → `android_class:Message`
- `android_api:AuthInterceptor` → `android_class:SessionManager`
- `android_class:BootReceiver` → `android_class:CallRecordingPreferences`
- `android_class:BootReceiver` → `android_class:CallRecordingService`
- `android_class:CallReceiver` → `android_class:CallRecordingService`
- `android_class:CallReceiver` → `android_class:CallRecordingPreferences`
- `android_class:CallRecordingService` → `android_activity:MainActivity`
- `android_class:CallRecordingService` → `android_class:CallUploadWorker`
- `android_class:CallRecordingService` → `android_class:CallRecordingPreferences`
- `android_class:CallUploadWorker` → `android_class:CallRecordingPreferences`
- `android_class:CallUploadWorker` → `android_class:Message`
- `android_class:DataCleanupWorker` → `android_class:EldoleadoApplication`
- `android_class:EldoleadoApplication` → `android_class:DataCleanupWorker`
- `android_class:EldoleadoApplication` → `android_class:AppDatabase`
- `android_class:EldoleadoApplication` → `android_api:RetrofitClient`
- `android_class:EldoleadoApplication` → `android_dao:AppealDao`
- `android_class:EldoleadoApplication` → `android_dao:MessageDao`
- `android_class:EldoleadoApplication` → `android_api_service:ApiService`
- `android_fcm:EldoleadoMessagingService` → `android_api:RetrofitClient`
- `android_fcm:EldoleadoMessagingService` → `android_class:AppealUpdateEvent`
- *...and 39 more*

---

## Recent Changes

| Date | Type | Component | Description |
|------|------|-----------|-------------|
| 2025-12-08 14:37 | created | BAT_AI_Appeal_Router | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-08 14:18 | created | message_processing_logic | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-07 12:22 | created | redis-debug-guide | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-07 12:18 | created | BAT_IN_Telegram | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-07 09:36 | created | avito_parser | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 14:49 | created | stop | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 14:49 | created | start | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 13:25 | created | tenant_ai_instructions | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 12:32 | created | MCP_DOCS-RAG | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 12:32 | created | Админка_Спецификация | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 12:32 | created | НАВИГАЦИЯ_ПРОЕКТА | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 12:32 | created | Настройка стабильности | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-06 12:32 | created | Масштаб_и_стабильность | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 13:04 | created | StereoCallRecorder | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 13:04 | created | RootRecordingPreferences | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 13:04 | created | RecordingTileService | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 13:04 | created | BAT_Queue_Processor | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 13:04 | created | RootChecker | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 10:57 | created | StCRM OUT.Telegram (Adapter) | Auto-discovered by populate_knowledge_base.py... |
| 2025-12-05 10:57 | created | MCP_TELEGRAM | Auto-discovered by populate_knowledge_base.py... |
