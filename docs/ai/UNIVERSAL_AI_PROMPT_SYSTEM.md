# Universal AI Prompt System - Архитектура

## Дата: 2025-11-23
## Статус: Концепция / RFC (Request for Comments)

---

## 🎯 Проблема

Сейчас в системе BatteryCRM используется подход с отдельными AI Tool workflows:
- BAT AI Appeal Type Classifier
- BAT AI Device Extractor
- BAT AI Issue Extractor
- BAT AI Multi-Entity Extractor (новый)
- BAT AI Response Generator
- BAT AI Voice Response Formatter

**Проблемы текущего подхода:**

1. **Дублирование кода**: каждый workflow имеет одинаковую структуру (Load Data → Build Prompt → Call AI → Parse Response)
2. **Негибкость**: для добавления нового AI Tool нужно создавать новый workflow в n8n
3. **Сложность изменений**: промпты захардкожены в коде workflow
4. **Отсутствие масштабируемости**: нельзя запустить параллельно N workers для ускорения
5. **Нет переиспользования**: каждый tool работает изолированно, не знает о результатах других

---

## 💡 Решение: Universal AI Prompt System

**Идея:** Создать универсальную систему, где:
- ✅ Все промпты хранятся в БД
- ✅ Один универсальный AI Worker выполняет любые промпты
- ✅ Динамический оркестратор определяет какие промпты запускать
- ✅ Очереди через Redis позволяют масштабировать workers
- ✅ Meta-AI может создавать новые промпты по требованию

---

## 🏗️ Архитектура

### Общая схема

```
┌─────────────────────────────────────────────────────────────────┐
│                    Входящее сообщение клиента                   │
│          "У меня два телефона: iPhone 14 и Samsung S23"         │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                     Meta-AI Prompt Generator                    │
│  Анализирует сообщение и определяет какие промпты нужны        │
│  "Нужно: multi_device_extract + brand_extract + model_extract"  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Prompt Orchestrator                        │
│  1. Читает промпты из БД (ai_prompts)                          │
│  2. Определяет порядок выполнения (dependencies)               │
│  3. Формирует контекст (результаты предыдущих промптов)        │
│  4. Отправляет задачи в Redis Queue                            │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                          Redis Queue                            │
│  Задачи:                                                        │
│  - Task 1: multi_device_extract (priority: 1)                  │
│  - Task 2: brand_extract (priority: 2, depends_on: Task 1)     │
│  - Task 3: model_extract (priority: 2, depends_on: Task 1)     │
└────────────────────────────┬────────────────────────────────────┘
                             │
                    ┌────────┴────────┬────────────┐
                    ▼                 ▼            ▼
           ┌─────────────────┐ ┌─────────────┐ ┌─────────────┐
           │  AI Worker #1   │ │ AI Worker #2│ │ AI Worker #N│
           │  (n8n workflow) │ │             │ │             │
           └─────────────────┘ └─────────────┘ └─────────────┘
                    │                 │            │
                    └────────┬────────┴────────────┘
                             │ Возвращают результаты
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                      Result Aggregator                          │
│  Собирает результаты всех workers и формирует финальный ответ  │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             ▼
┌─────────────────────────────────────────────────────────────────┐
│                   Сохранение в БД / Ответ клиенту               │
└─────────────────────────────────────────────────────────────────┘
```

---

## 📊 Структура БД

### 1. ai_prompts — Библиотека промптов

```sql
CREATE TABLE ai_prompts (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id uuid REFERENCES tenants(id),

  -- Основные поля
  name varchar NOT NULL UNIQUE,
  display_name varchar NOT NULL,
  description text,
  category varchar, -- 'extraction', 'classification', 'generation', 'formatting'

  -- Промпт
  prompt_template text NOT NULL, -- Шаблон с переменными {{variable}}
  system_prompt text, -- Системный промпт (опционально)

  -- Схемы данных
  input_schema jsonb NOT NULL, -- JSON Schema для входных данных
  output_schema jsonb NOT NULL, -- JSON Schema для выходных данных

  -- Зависимости и порядок
  dependencies varchar[], -- ['brand_extract', 'model_extract']
  execution_order int DEFAULT 0, -- Порядок выполнения (меньше = раньше)
  priority int DEFAULT 0, -- Приоритет (выше = важнее)

  -- Конфигурация AI модели
  ai_model varchar DEFAULT 'claude-3-5-sonnet-20241022',
  max_tokens int DEFAULT 4096,
  temperature numeric(3,2) DEFAULT 0.7,

  -- Контекст и кеширование
  requires_context varchar[], -- Какие данные нужны из контекста
  cache_ttl_seconds int, -- Время кеширования результата

  -- Метаданные
  is_active boolean DEFAULT true,
  version int DEFAULT 1,
  created_by uuid REFERENCES operators(id),
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE INDEX idx_ai_prompts_tenant ON ai_prompts(tenant_id);
CREATE INDEX idx_ai_prompts_name ON ai_prompts(name);
CREATE INDEX idx_ai_prompts_category ON ai_prompts(category);
CREATE INDEX idx_ai_prompts_active ON ai_prompts(is_active);
```

**Пример промпта:**

```sql
INSERT INTO ai_prompts (name, display_name, category, prompt_template, input_schema, output_schema, dependencies)
VALUES (
  'multi_device_extract',
  'Multi-Device Extractor',
  'extraction',

  -- Промпт
  'Извлеки из сообщения клиента информацию о всех устройствах и их неисправностях.

   Сообщение: {{message}}

   Доступные бренды: {{brands}}
   Доступные категории ремонта: {{repair_categories}}

   Верни JSON в формате:
   {
     "devices": [
       {
         "brand": "Apple",
         "model": "iPhone 14",
         "repairs": [
           {"category": "Дисплей", "symptoms": "разбит экран"}
         ]
       }
     ]
   }',

  -- Input schema
  '{
    "type": "object",
    "required": ["message", "brands", "repair_categories"],
    "properties": {
      "message": {"type": "string"},
      "brands": {"type": "array"},
      "repair_categories": {"type": "array"}
    }
  }',

  -- Output schema
  '{
    "type": "object",
    "required": ["devices"],
    "properties": {
      "devices": {
        "type": "array",
        "items": {
          "type": "object",
          "properties": {
            "brand": {"type": "string"},
            "model": {"type": "string"},
            "repairs": {"type": "array"}
          }
        }
      }
    }
  }',

  -- Dependencies (выполняется первым, зависимостей нет)
  ARRAY[]::varchar[]
);
```

---

### 2. ai_tasks — Задачи для выполнения

```sql
CREATE TABLE ai_tasks (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id uuid REFERENCES tenants(id),

  -- Связь с заявкой/контекстом
  appeal_id uuid REFERENCES appeals(id),
  parent_task_id uuid REFERENCES ai_tasks(id), -- Для цепочек задач

  -- Промпт
  prompt_id uuid REFERENCES ai_prompts(id),
  prompt_name varchar NOT NULL, -- Дубликат для быстрого поиска

  -- Входные данные
  input_data jsonb NOT NULL,

  -- Статус выполнения
  status varchar DEFAULT 'pending', -- pending, processing, completed, failed
  worker_id varchar, -- ID worker, который обрабатывает

  -- Результат
  output_data jsonb,
  error_message text,

  -- Метрики
  started_at timestamp,
  completed_at timestamp,
  execution_time_ms int,
  ai_cost numeric(10, 6),

  -- Метаданные
  created_at timestamp DEFAULT now(),
  updated_at timestamp DEFAULT now()
);

CREATE INDEX idx_ai_tasks_tenant ON ai_tasks(tenant_id);
CREATE INDEX idx_ai_tasks_appeal ON ai_tasks(appeal_id);
CREATE INDEX idx_ai_tasks_status ON ai_tasks(status);
CREATE INDEX idx_ai_tasks_prompt ON ai_tasks(prompt_id);
CREATE INDEX idx_ai_tasks_created ON ai_tasks(created_at);

-- CHECK constraint
ALTER TABLE ai_tasks ADD CONSTRAINT chk_ai_tasks_status
  CHECK (status IN ('pending', 'processing', 'completed', 'failed', 'cancelled'));
```

---

### 3. ai_task_results — Кеш результатов

```sql
CREATE TABLE ai_task_results (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id uuid REFERENCES tenants(id),

  -- Ключ кеша (hash от prompt_name + input_data)
  cache_key varchar NOT NULL,

  -- Промпт
  prompt_id uuid REFERENCES ai_prompts(id),
  prompt_name varchar NOT NULL,

  -- Данные
  input_data jsonb NOT NULL,
  output_data jsonb NOT NULL,

  -- TTL
  expires_at timestamp,

  -- Метаданные
  created_at timestamp DEFAULT now()
);

CREATE INDEX idx_ai_task_results_cache_key ON ai_task_results(cache_key);
CREATE INDEX idx_ai_task_results_expires ON ai_task_results(expires_at);
CREATE INDEX idx_ai_task_results_tenant ON ai_task_results(tenant_id);

-- UNIQUE constraint
ALTER TABLE ai_task_results ADD CONSTRAINT uk_ai_task_results_cache
  UNIQUE (tenant_id, cache_key);
```

---

### 4. ai_meta_prompts — Meta-AI промпты

```sql
CREATE TABLE ai_meta_prompts (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  tenant_id uuid REFERENCES tenants(id),

  -- Описание задачи
  task_description text NOT NULL, -- "Извлечь устройства из сообщения"

  -- Сгенерированный промпт
  generated_prompt text NOT NULL,
  generated_at timestamp DEFAULT now(),

  -- Использование
  usage_count int DEFAULT 0,
  last_used_at timestamp,

  -- Feedback (насколько хорошо работает)
  success_rate numeric(5,2), -- % успешных выполнений
  avg_execution_time_ms int,

  -- Статус
  is_approved boolean DEFAULT false, -- Одобрен для production
  approved_by uuid REFERENCES operators(id),
  approved_at timestamp,

  -- Метаданные
  created_at timestamp DEFAULT now()
);

CREATE INDEX idx_ai_meta_prompts_tenant ON ai_meta_prompts(tenant_id);
CREATE INDEX idx_ai_meta_prompts_approved ON ai_meta_prompts(is_approved);
```

---

## 🔧 Компоненты системы

### 1. Meta-AI Prompt Generator

**Назначение:** Анализирует входящее сообщение и определяет какие промпты нужны.

**Workflow (n8n):**

```javascript
// Node 1: Analyze Message
const message = $input.item.json.message;

// Вызов Claude AI для анализа
const analysis = await callClaudeAI({
  prompt: `Проанализируй сообщение клиента и определи какие AI-промпты нужны.

  Доступные промпты:
  - multi_device_extract: извлечение нескольких устройств
  - single_device_extract: извлечение одного устройства
  - brand_extract: определение бренда
  - model_extract: определение модели
  - issue_extract: определение неисправности
  - appeal_type_classify: классификация типа обращения

  Сообщение: "${message}"

  Верни JSON со списком нужных промптов в порядке выполнения.`,

  message: message
});

// Node 2: Load Prompts from DB
const promptNames = analysis.required_prompts;
const prompts = await db.query(`
  SELECT * FROM ai_prompts
  WHERE name = ANY($1) AND is_active = true
  ORDER BY execution_order ASC
`, [promptNames]);

// Node 3: Create Tasks
for (const prompt of prompts) {
  await createAITask({
    prompt_id: prompt.id,
    input_data: {
      message: message,
      ...context
    }
  });
}
```

---

### 2. Prompt Orchestrator

**Назначение:** Управляет выполнением задач, учитывает зависимости.

**Workflow:**

```javascript
// Node 1: Get Pending Tasks
const tasks = await db.query(`
  SELECT t.*, p.dependencies, p.requires_context
  FROM ai_tasks t
  JOIN ai_prompts p ON p.id = t.prompt_id
  WHERE t.status = 'pending'
    AND t.tenant_id = $1
  ORDER BY p.priority DESC, p.execution_order ASC
`, [tenant_id]);

// Node 2: Check Dependencies
for (const task of tasks) {
  // Проверить зависимости
  const dependencies = task.dependencies || [];
  const dependenciesCompleted = await checkDependencies(task.appeal_id, dependencies);

  if (!dependenciesCompleted) {
    continue; // Пропустить задачу, зависимости не выполнены
  }

  // Node 3: Build Context
  const context = await buildContext(task);

  // Node 4: Push to Redis Queue
  await redisQueue.add('ai-task-queue', {
    task_id: task.id,
    prompt_template: prompt.prompt_template,
    input_data: {
      ...task.input_data,
      ...context
    }
  });
}
```

---

### 3. Universal AI Worker

**Назначение:** Универсальный исполнитель промптов.

**Workflow (n8n):**

```javascript
// Node 1: Listen to Redis Queue
const job = await redisQueue.process('ai-task-queue', async (job) => {
  const { task_id, prompt_template, input_data } = job.data;

  // Node 2: Update Task Status
  await db.query(`
    UPDATE ai_tasks
    SET status = 'processing', started_at = now(), worker_id = $1
    WHERE id = $2
  `, [worker_id, task_id]);

  // Node 3: Build Prompt
  const prompt = renderTemplate(prompt_template, input_data);

  // Node 4: Call AI
  const result = await callClaudeAI({
    prompt: prompt,
    max_tokens: input_data.max_tokens || 4096,
    temperature: input_data.temperature || 0.7
  });

  // Node 5: Parse Response
  const output = parseAIResponse(result);

  // Node 6: Validate Output
  const isValid = validateOutput(output, input_data.output_schema);

  if (!isValid) {
    throw new Error('Output validation failed');
  }

  // Node 7: Save Result
  await db.query(`
    UPDATE ai_tasks
    SET
      status = 'completed',
      output_data = $1,
      completed_at = now(),
      execution_time_ms = EXTRACT(EPOCH FROM (now() - started_at)) * 1000
    WHERE id = $2
  `, [output, task_id]);

  // Node 8: Cache Result (if cacheable)
  if (input_data.cache_ttl_seconds) {
    await cacheResult(task_id, output, input_data.cache_ttl_seconds);
  }

  return output;
});
```

---

### 4. Result Aggregator

**Назначение:** Собирает результаты всех tasks для одной заявки.

```javascript
// Get all completed tasks for appeal
const results = await db.query(`
  SELECT t.prompt_name, t.output_data
  FROM ai_tasks t
  WHERE t.appeal_id = $1 AND t.status = 'completed'
  ORDER BY t.completed_at ASC
`, [appeal_id]);

// Aggregate results
const aggregated = {
  appeal_id: appeal_id,
  tasks: results.map(r => ({
    prompt: r.prompt_name,
    data: r.output_data
  })),

  // Извлечь данные из конкретных промптов
  devices: results.find(r => r.prompt_name === 'multi_device_extract')?.output_data?.devices,
  appeal_type: results.find(r => r.prompt_name === 'appeal_type_classify')?.output_data?.type,
  ...
};

return aggregated;
```

---

## 🚀 Преимущества новой системы

### 1. Гибкость
- ✅ Промпты настраиваются в БД (не нужно менять код)
- ✅ Можно A/B тестировать промпты
- ✅ Версионирование промптов

### 2. Масштабируемость
- ✅ Можно запустить N workers параллельно
- ✅ Redis Queue обеспечивает распределение нагрузки
- ✅ Горизонтальное масштабирование

### 3. Переиспользование
- ✅ Workers знают о результатах других workers (через context)
- ✅ Кеширование результатов
- ✅ Зависимости между промптами

### 4. Эволюция
- ✅ Meta-AI создаёт новые промпты по требованию
- ✅ Система учится на успешных промптах
- ✅ Автоматическая оптимизация

### 5. Мониторинг
- ✅ Логирование всех задач в БД
- ✅ Метрики выполнения
- ✅ Стоимость AI вызовов

---

## 📝 План миграции

### Этап 1: Создание инфраструктуры (1 неделя)
1. Создать таблицы БД (ai_prompts, ai_tasks, ai_task_results, ai_meta_prompts)
2. Настроить Redis на сервере
3. Создать Universal AI Worker workflow
4. Создать Prompt Orchestrator workflow

### Этап 2: Миграция существующих промптов (3 дня)
1. Перенести Multi-Entity Extractor → ai_prompts
2. Перенести Device Extractor → ai_prompts
3. Перенести Issue Extractor → ai_prompts
4. Перенести Appeal Type Classifier → ai_prompts

### Этап 3: Интеграция (3 дня)
1. Обновить BAT AI Appeal Router для использования Orchestrator
2. Тестирование на dev окружении
3. Постепенный rollout на production (10% → 50% → 100%)

### Этап 4: Meta-AI (1 неделя)
1. Создать Meta-AI Prompt Generator
2. Тестирование генерации промптов
3. Система одобрения сгенерированных промптов

---

## 💰 Стоимость

**Инфраструктура:**
- Redis (managed): $10-20/месяц
- Дополнительные n8n workers: бесплатно (self-hosted)

**AI вызовы:**
- Текущая стоимость: ~$12/месяц (1000 заявок)
- После оптимизации (кеширование): ~$8/месяц

**ROI:**
- Экономия времени разработки: ~40 часов/месяц
- Гибкость: новые промпты за минуты, не часы
- Масштабируемость: можно обрабатывать 10x больше заявок

---

## ⚠️ Риски и митигация

**Риск 1: Сложность системы**
- Митигация: Постепенная миграция, параллельный запуск старой и новой систем

**Риск 2: Redis Single Point of Failure**
- Митигация: Redis Sentinel для HA, fallback на direct execution

**Риск 3: Медленное выполнение из-за зависимостей**
- Митигация: Параллельное выполнение независимых промптов, приоритизация

**Риск 4: Качество сгенерированных промптов (Meta-AI)**
- Митигация: Система одобрения, A/B тестирование

---

## 📊 Метрики успеха

1. **Время добавления нового AI Tool**: было 4 часа → станет 10 минут
2. **Пропускная способность**: было 10 заявок/сек → станет 100 заявок/сек
3. **Стоимость AI вызовов**: было $12/месяц → станет $8/месяц (кеширование)
4. **Время отклика**: было 3-5 сек → станет 1-2 сек (параллелизм)

---

## 🎯 Следующие шаги

1. **Обсуждение архитектуры** с командой
2. **Проверка концепции (PoC)**: создать минимальную версию с 1-2 промптами
3. **Тестирование** на реальных данных
4. **Постепенная миграция** существующих AI Tools
5. **Запуск Meta-AI** для генерации новых промптов

---

**Автор:** User + Claude Code
**Дата:** 2025-11-23
**Статус:** RFC (Request for Comments)
**Версия:** 1.0
