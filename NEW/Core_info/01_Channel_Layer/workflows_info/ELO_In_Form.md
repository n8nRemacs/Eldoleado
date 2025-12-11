# ELO_In_Form

> Incoming workflow for Web forms and quizzes

---

## General Information

| Parameter | Value |
|----------|----------|
| **File** | `NEW/workflows/ELO_In/ELO_In_Form.json` |
| **Trigger** | Webhook POST `/form` |
| **Called from** | Landing pages, quizzes, feedback forms |
| **Calls** | Execute Client Resolver, ELO_Core_Tenant_Resolver |
| **Output** | HTTP Response (NO Redis queue!) |

---

## Purpose

Receives requests from web forms, normalizes contact data and creates client/dialog.

**Feature:** Does not use Redis queue — processes directly through Client Resolver.

---

## Input Data

```json
{
  "phone": "+79991234567",
  "name": "Иван Петров",
  "phone_model": "iPhone 14",
  "message": "Разбил экран",
  "email": "ivan@example.com",
  "source": "quiz_landing",
  "form_id": "repair-quiz-1",
  "form_name": "Квиз ремонт телефонов"
}
```

**Supported field variants:**
- phone / telephone / mobile / cell
- name / full_name / client_name / customer_name
- phone_model / model / device_model / device
- message / comment / description / text

---

## Output Data

**HTTP Response:**
```json
{
  "success": true,
  "message": "Спасибо! Мы свяжемся с вами в ближайшее время."
}
```

---

## Nodes

### 1. Form Trigger

| Parameter | Value |
|----------|----------|
| **ID** | `39820b0b-a59e-41d8-b548-c397a65d1f5b` |
| **Path** | `/form` |
| **Response Mode** | lastNode |

---

### 2. Normalize Form

| Parameter | Value |
|----------|----------|
| **ID** | `2a784909-75d9-46fa-9dba-3be9ae9ba689` |
| **Type** | n8n-nodes-base.code |

**Code:**
```javascript
const formData = $input.first().json;

// Извлекаем данные из формы/квиза
const phone = formData.phone || formData.telephone || formData.mobile || formData.cell || null;
const name = formData.name || formData.full_name || formData.client_name || null;
const phoneModel = formData.phone_model || formData.model || formData.device_model || null;
const message = formData.message || formData.comment || formData.description || formData.text || '';
const email = formData.email || null;
const source = formData.source || formData.utm_source || formData.form_source || 'form';
const formId = formData.form_id || formData.quiz_id || formData.id || null;
const formName = formData.form_name || formData.quiz_name || null;

// Формируем текст сообщения
let messageText = '';
if (phoneModel) {
  messageText += `Модель: ${phoneModel}\n`;
}
if (message) {
  messageText += message;
}
if (!messageText.trim()) {
  messageText = '[Заявка с формы без текста]';
}

// Нормализация телефона
let cleanPhone = null;
if (phone) {
  cleanPhone = phone.replace(/\D/g, '');
  // 8 → 7
  if (cleanPhone.length === 11 && cleanPhone.startsWith('8')) {
    cleanPhone = '7' + cleanPhone.substring(1);
  }
  // 9xxxxxxxxx → 7
  if (cleanPhone.length === 10 && cleanPhone.startsWith('9')) {
    cleanPhone = '7' + cleanPhone;
  }
  cleanPhone = '+' + cleanPhone;
}

return {
  channel: 'form',
  external_user_id: cleanPhone?.replace(/\+/g, '') || email || 'unknown',
  external_chat_id: cleanPhone || email || 'unknown',

  text: messageText.trim(),
  timestamp: new Date().toISOString(),

  client_phone: cleanPhone,
  client_name: name,
  client_email: email,

  media: {
    has_voice: false,
    has_images: false,
    has_video: false,
    has_document: false
  },

  meta: {
    external_message_id: formId || Date.now().toString(),
    ad_channel: 'form',
    form_source: source,
    form_id: formId,
    form_name: formName
  },

  prefilled_data: {
    model: phoneModel,  // <-- prefilled model!
    parts_owner: null
  }
};
```

---

### 3. Execute Client Resolver

| Parameter | Value |
|----------|----------|
| **ID** | `e650cfe1-5df0-4c05-823c-5c01eaca330f` |
| **Calls** | `$env.CLIENT_RESOLVER_WORKFLOW_ID` |

---

### 4. Execute Tenant Resolver

| Parameter | Value |
|----------|----------|
| **ID** | `9168577b-2691-4a26-abbd-0333e92cf428` |
| **Calls** | ELO_Core_Tenant_Resolver (rRO6sxLqiCdgvLZz) |

---

### 5. Respond Success

```json
{
  "success": true,
  "message": "Спасибо! Мы свяжемся с вами в ближайшее время."
}
```

---

## Flow Schema

```
Form Trigger → Normalize Form → Execute Client Resolver → Execute Tenant Resolver → Respond Success
```

**NO Redis!** Form is processed synchronously.

---

## Features

| Feature | Description |
|-------------|----------|
| **No queue** | Synchronous processing, no Redis |
| **prefilled_data.model** | Phone model from form goes directly to prefilled |
| **Phone normalization** | 8→7, adding missing 7 |
| **Fallback ID** | email or 'unknown' if no phone |
| **form_source** | UTM tag or source name |

---

## Env Variables

| Variable | Description |
|------------|----------|
| `CLIENT_RESOLVER_WORKFLOW_ID` | Workflow ID for finding/creating client |
