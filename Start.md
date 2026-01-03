# Start Session - 2026-01-03 ~18:00

## Статус: Pipeline разорван, нужна починка

---

## КРИТИЧЕСКАЯ ПРОБЛЕМА

Pipeline разорван между Resolver и AI Orchestrator:

```
ELO_Resolver → webhook /elo-core-ingest → Test_Stub [OFF] ← ТУПИК!

Pipeline_Orchestrator [OFF] ← НИКТО НЕ ВЫЗЫВАЕТ!
```

---

## СЛЕДУЮЩИЕ ШАГИ (Фаза 1 - КРИТИЧНО)

### 1.1 Починить Resolver → Pipeline

**Файл:** `NEW/workflows/Resolve Contour/ELO_Resolver.json`

**Действия:**
1. Найти ноду "Forward to Core" (HTTP Request → `/elo-core-ingest`)
2. Заменить на Execute Workflow → ELO_Pipeline_Orchestrator
3. Передать параметры: tenant_id, client_id, dialog_id, message, operator_id, mode

### 1.2 Добавить Mode Router в Pipeline

**Файл:** `NEW/workflows/AI Contour/ELO_Pipeline_Orchestrator.json`

**Действия:**
1. После Context Building добавить Switch по mode:
   - `manual` → Skip Generate → Notify Operator
   - `semi_auto` → Generate → Notify with Draft
   - `auto` → Generate → Auto Send / Escalate

### 1.3 Создать ELO_Operator_Notify

**Новый workflow:**
```
Input: { operator_id, dialog_id, notification_type, context, draft }
Nodes:
1. Get Operator FCM tokens
2. Build Notification Payload
3. Send WebSocket → api-android:8780
4. Send FCM Push
5. Log Event
```

### 1.4 Включить workflows

- ELO_Resolver → ON
- ELO_Pipeline_Orchestrator → ON

---

## Режимы оператора (уже спроектировано)

| Режим | Generate | Approve | Описание |
|-------|----------|---------|----------|
| `manual` | ❌ | ❌ | Оператор пишет сам |
| `semi_auto` | ✅ | ✅ | AI генерирует, оператор утверждает |
| `auto` | ✅ | ❌ | AI отправляет сразу |

**Pipeline работает ОДИНАКОВО для всех режимов!**
Разница только в генерации/утверждении ответа.

---

## Документация

| Файл | Описание |
|------|----------|
| `NEW/DOCS/WORKFLOWS_ANALYSIS.md` | Анализ 57 workflows |
| `NEW/DOCS/OPERATOR_NOTIFICATION_ARCHITECTURE.md` | Архитектура 3 режимов |
| `123.md` | Полный отчёт + план |
| `~/.claude/plans/adaptive-sprouting-stream.md` | План Funnel Behavior |

---

## Текущее состояние workflows

| Категория | Active | Inactive |
|-----------|--------|----------|
| Channel In | 8 | 3 |
| Channel Out | 4 | 3 |
| API | 11 | 0 |
| AI Contour | 4 | 11 |
| Input/Resolve/Core | 2 | 11 |
| **Total** | **26** | **31** |

---

## Инфраструктура

| Сервер | IP | Сервисы |
|--------|-----|---------|
| **Messenger** | 155.212.221.189 | mcp-* :876x, api-android :8780 |
| **n8n** | 185.221.214.83 | n8n :5678, PostgreSQL :6544 |

---

## Проверка БД

```bash
# Режим тенанта
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"SELECT name, settings->'ai_mode' FROM elo_t_tenants;\""

# Режим оператора
ssh root@185.221.214.83 "docker exec supabase-db psql -U postgres -c \"SELECT name, settings->'ai_mode' FROM elo_t_operators;\""
```

---

*Последнее обновление: 2026-01-03 ~18:00*
