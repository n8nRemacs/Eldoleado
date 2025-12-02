# ⚠️ ВАЖНО: Версия n8n

## 🔧 Текущая версия
**n8n v1.119.2**

## 📚 Документация для этой версии
- **Официальная документация:** https://docs.n8n.io/
- **API Reference:** https://docs.n8n.io/api/
- **Change Log:** https://github.com/n8n-io/n8n/releases/tag/n8n%401.119.2

## ⚠️ Критические моменты совместимости

### 1. Nodes (Ноды)
- Всегда проверять `typeVersion` нод - в v1.119.2 могут быть устаревшие версии
- Некоторые ноды могли быть добавлены в более поздних версиях

### 2. AI Nodes (@n8n/n8n-nodes-langchain)
В версии 1.119.2 доступны:
- ✅ AI Agent (typeVersion: 1.7)
- ✅ OpenAI Chat Model
- ✅ Tool Workflow
- ✅ Memory: Buffer Window
- ✅ Postgres Chat Memory

### 3. Core Nodes (n8n-nodes-base)
Используемые в проекте:
- ✅ Telegram Trigger (typeVersion: 1.2)
- ✅ Webhook (typeVersion: 2.1)
- ✅ PostgreSQL (typeVersion: 2.6)
- ✅ Code (typeVersion: 2)
- ✅ Execute Workflow (typeVersion: 1.2)
- ✅ HTTP Request (typeVersion: 4.2)
- ✅ Switch (typeVersion: 3.2)
- ✅ IF (typeVersion: 2.2)
- ✅ Merge (typeVersion: 3.2)
- ✅ Set (Edit Fields) (typeVersion: 3.4)

### 4. Что НЕ работает в 1.119.2
❌ Функции из версий > 1.119.2 использовать НЕЛЬЗЯ
❌ Проверять совместимость при добавлении новых нод
❌ Новые версии typeVersion могут не работать

## 🔍 Проверка совместимости перед изменениями

### Перед добавлением новой ноды:
1. Проверить есть ли она в v1.119.2
2. Проверить поддерживаемый typeVersion
3. Проверить доступные параметры

### Перед обновлением воркера:
1. Убедиться что все ноды совместимы
2. Проверить что typeVersion не выше поддерживаемой
3. Тестировать на staging перед продакшном

## 📖 Ссылки на документацию нод v1.119.2

### AI & LangChain:
- AI Agent: https://docs.n8n.io/integrations/builtin/cluster-nodes/root-nodes/n8n-nodes-langchain.agent/
- OpenAI: https://docs.n8n.io/integrations/builtin/cluster-nodes/sub-nodes/n8n-nodes-langchain.lmchatopenai/
- Tool Workflow: https://docs.n8n.io/integrations/builtin/cluster-nodes/sub-nodes/n8n-nodes-langchain.toolworkflow/

### Core:
- Webhook: https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.webhook/
- PostgreSQL: https://docs.n8n.io/integrations/builtin/app-nodes/n8n-nodes-base.postgres/
- Code: https://docs.n8n.io/code/builtin/code-node/
- Execute Workflow: https://docs.n8n.io/integrations/builtin/core-nodes/n8n-nodes-base.executeworkflow/

### Messaging:
- Telegram: https://docs.n8n.io/integrations/builtin/app-nodes/n8n-nodes-base.telegram/
- Telegram Trigger: https://docs.n8n.io/integrations/builtin/trigger-nodes/n8n-nodes-base.telegramtrigger/

## 🚨 ПРАВИЛО для Claude:

**ПЕРЕД любым изменением воркера:**
1. ✅ Проверить что нода существует в v1.119.2
2. ✅ Проверить typeVersion совместимость
3. ✅ Убедиться что параметры поддерживаются
4. ✅ НЕ использовать функции из более новых версий
5. ✅ При сомнениях - проверить документацию для v1.119.2

**Если функция не была в v1.119.2 - искать альтернативный способ!**
