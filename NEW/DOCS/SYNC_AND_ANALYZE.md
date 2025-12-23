# Sync and Analyze Workflows

> Инструкция для Claude: синхронизировать workflows из n8n и обновить анализ

---

## Команда для запуска

Просто скажи:
```
Выполни инструкцию из NEW/workflows/SYNC_AND_ANALYZE.md
```

---

## Инструкция для Claude

### Шаг 1: Синхронизация из n8n

1. Получить список всех workflows с тегом "ELO" через n8n API:
```bash
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows?tags=ELO" \
  -H "X-N8N-API-KEY: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzY0NzY4NzE0LCJleHAiOjE3NzI0ODE2MDB9.JzC21XpXh7188Qlx2xWpZPHQysksg_Jj0hWuTgy6PmQ"
```

2. Для каждого workflow получить полный JSON:
```bash
curl -s "https://n8n.n8nsrv.ru/api/v1/workflows/{id}" \
  -H "X-N8N-API-KEY: ..."
```

3. Сохранить в соответствующую папку по тегам:
   - Tag "ELO" + "In" → `Chanel Contour/ELO_In/`
   - Tag "ELO" + "Out" → `Chanel Contour/ELO_Out/`
   - Tag "ELO" + "Input" → `Input Contour/`
   - Tag "ELO" + "Client" → `Client Contour/`
   - Tag "ELO" + "AI" → `AI Contour/`
   - Tag "ELO" + "API" → `API/`
   - Tag "ELO" + "Graph" → `Graph Contour/`

4. НЕ синхронизировать:
   - Архивные workflows (tag "Archive" или "Old")
   - Папку `n8n_old/`
   - Workflows без тега "ELO"

### Шаг 2: Анализ

После синхронизации:
1. Прочитать все JSON файлы в папках (кроме n8n_old, reference)
2. Проанализировать каждый workflow:
   - Назначение
   - Триггер (webhook/schedule/execute)
   - Ноды и их последовательность
   - Connections (что куда ведёт)
3. Построить общую диаграмму потока данных
4. Найти разрывы в цепочках

### Шаг 3: Обновить документацию

Обновить файл `WORKFLOWS_ANALYSIS.md`:
- Дата последнего обновления
- Новые/изменённые workflows
- Актуальные диаграммы
- Новые gaps/issues

---

## Маппинг тегов → папки

| n8n Tags | Target Folder |
|----------|---------------|
| ELO, In | `Chanel Contour/ELO_In/` |
| ELO, Out | `Chanel Contour/ELO_Out/` |
| ELO, Router | `Chanel Contour/` |
| ELO, Input | `Input Contour/` |
| ELO, Client | `Client Contour/` |
| ELO, AI | `AI Contour/` |
| ELO, API | `API/` |
| ELO, Graph | `Graph Contour/` |
| ELO, Core | корень или по смыслу |

---

## n8n API Reference

**Base URL:** `https://n8n.n8nsrv.ru/api/v1`

**Headers:**
```
X-N8N-API-KEY: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzY0NzY4NzE0LCJleHAiOjE3NzI0ODE2MDB9.JzC21XpXh7188Qlx2xWpZPHQysksg_Jj0hWuTgy6PmQ
```

**Endpoints:**
- `GET /workflows` - список всех workflows
- `GET /workflows?tags=ELO` - фильтр по тегу
- `GET /workflows/{id}` - полный workflow

**ВАЖНО:** API только для чтения! Не модифицировать workflows через API.

---

## Файловая структура

```
NEW/workflows/
├── SYNC_AND_ANALYZE.md      # Эта инструкция
├── WORKFLOWS_ANALYSIS.md    # Результат анализа
├── Chanel Contour/
│   ├── ELO_In/
│   │   ├── ELO_In_WhatsApp.json
│   │   ├── ELO_In_Telegram.json
│   │   └── ...
│   ├── ELO_Out/
│   │   ├── ELO_Out_WhatsApp.json
│   │   └── ...
│   └── ELO_Out_Router.json
├── Input Contour/
│   ├── ELO_Input_Batcher.json
│   ├── ELO_Input_Processor.json
│   └── ...
├── Client Contour/
│   └── ELO_Client_Resolve.json
├── AI Contour/
│   └── ELO_Core_AI.json
├── API/
│   ├── ELO_API_Android_Auth.json
│   └── ...
├── Graph Contour/
│   └── ELO_Graph_Query.json
└── n8n_old/                  # НЕ ТРОГАТЬ
```

---

## Чеклист выполнения

- [ ] Синхронизировать workflows с тегом ELO из n8n
- [ ] Сохранить JSON файлы в правильные папки
- [ ] Удалить устаревшие локальные файлы (если удалены в n8n)
- [ ] Прочитать все синхронизированные файлы
- [ ] Проанализировать каждый workflow
- [ ] Построить диаграмму потока
- [ ] Найти разрывы и проблемы
- [ ] Обновить WORKFLOWS_ANALYSIS.md
- [ ] Сообщить о изменениях пользователю

---

*Последнее обновление инструкции: 2025-12-20*
