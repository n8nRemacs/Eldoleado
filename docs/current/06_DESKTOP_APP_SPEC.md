# BatteryCRM - Desktop App: Техническая спецификация

**Версия:** 2.0
**Дата:** 2025-11-23
**Статус:** Актуальная спецификация
**Платформы:** Windows 10+, macOS 11+, Linux (Ubuntu 20.04+)

---

## Содержание

1. [Обзор приложения](#обзор-приложения)
2. [Архитектура](#архитектура)
3. [Технологический стек](#технологический-стек)
4. [Экраны и навигация](#экраны-и-навигация)
5. [Функциональность](#функциональность)
6. [Аналитика и дашборды](#аналитика-и-дашборды)
7. [Массовые операции](#массовые-операции)
8. [Административная панель](#административная-панель)
9. [Multi-device sessions](#multi-device-sessions)
10. [Экспорт и отчёты](#экспорт-и-отчёты)

---

## Обзор приложения

### Назначение

**BatteryCRM Desktop App** — десктопное приложение для операторов и менеджеров сервисных центров, обеспечивающее:
- Полный функционал работы с заявками (как в Android app)
- Расширенную аналитику и дашборды
- Массовые операции с заявками
- Административные функции (управление операторами, настройки)
- Экспорт данных и формирование отчётов
- Работу в режиме 1 mobile + 1 desktop одновременно

### Целевая аудитория

- **Операторы** — обработка обращений на большом экране
- **Менеджеры** — аналитика, управление процессами
- **Администраторы тенанта** — настройка системы, управление пользователями

### Ключевые преимущества

1. **Большой экран** — удобная работа с множеством заявок
2. **Мультизадачность** — несколько заявок открыты одновременно (tabs)
3. **Аналитика** — визуализация метрик, графики, дашборды
4. **Массовые операции** — изменение этапов, назначение операторов
5. **Админка** — управление операторами, прайсами, промо-материалами
6. **Экспорт** — CSV, Excel, PDF для отчётности

---

## Архитектура

### Технологический выбор

**Вариант 1: Electron + React** (рекомендуется)
- Кроссплатформенность из коробки
- Единая кодовая база для Web/Desktop
- Большая экосистема библиотек
- Простая интеграция с backend API

**Вариант 2: Tauri + Vue/React**
- Меньший размер приложения
- Меньшее потребление ресурсов
- Rust backend для нативных функций

**Вариант 3: Web-приложение**
- Без установки (browser-based)
- Доступ с любого устройства
- Автоматические обновления

**Выбор:** Electron + React (TypeScript)

### Архитектура приложения

```
┌────────────────────────────────────────────────┐
│                UI LAYER                        │
│  React Components (Functional + Hooks)         │
│  - Pages, Layouts, Components                  │
│  - Material-UI / Ant Design                    │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│            STATE MANAGEMENT                    │
│  Redux Toolkit / Zustand                       │
│  - Global State (User, Appeals, Settings)      │
│  - Async Actions (Thunks)                      │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│               API LAYER                        │
│  Axios / Fetch                                 │
│  - REST API Client                             │
│  - WebSocket для real-time обновлений         │
└────────────────────────────────────────────────┘
                      ↓
┌────────────────────────────────────────────────┐
│            ELECTRON LAYER                      │
│  Main Process (Node.js)                        │
│  - Window Management                           │
│  - Notifications                               │
│  - File System Access                          │
│  - Auto-updates                                │
└────────────────────────────────────────────────┘
```

---

## Технологический стек

### Core

| Технология | Версия | Назначение |
|:-----------|:-------|:-----------|
| Electron | 28+ | Desktop framework |
| React | 18+ | UI библиотека |
| TypeScript | 5+ | Типизация |
| Vite | 5+ | Build tool |

### UI Framework

**Вариант 1: Material-UI (MUI)**
```typescript
import { ThemeProvider, createTheme } from '@mui/material/styles';
import { Button, TextField, Card } from '@mui/material';
```

**Вариант 2: Ant Design**
```typescript
import { Button, Table, Form, Modal } from 'antd';
```

**Выбор:** Ant Design (лучше для enterprise приложений)

### State Management

| Библиотека | Назначение |
|:-----------|:-----------|
| Redux Toolkit | Глобальное состояние |
| React Query | Кэширование API запросов |
| Zustand | Легковесное локальное состояние |

### Графики и визуализация

| Библиотека | Назначение |
|:-----------|:-----------|
| Recharts | Графики и диаграммы |
| Chart.js | Альтернатива для графиков |
| React Table | Таблицы с фильтрами |

### Utilities

| Библиотека | Назначение |
|:-----------|:-----------|
| Axios | HTTP клиент |
| Day.js | Работа с датами |
| React Hook Form | Формы |
| Yup | Валидация |
| Socket.io-client | WebSocket |
| ExcelJS | Экспорт в Excel |
| jsPDF | Экспорт в PDF |

---

## Экраны и навигация

### Структура навигации

```
┌─────────────────────────────────────────────┐
│            SIDEBAR MENU                     │
│  - Dashboard (Дашборд)                      │
│  - Appeals (Заявки)                         │
│  - Analytics (Аналитика)                    │
│  - Operators (Операторы)                    │
│  - Settings (Настройки)                     │
│  - Admin Panel (Админка)                    │
└─────────────────────────────────────────────┘
```

### Layout

```tsx
<Layout>
  <Sider>
    <Menu>
      <Menu.Item icon={<DashboardIcon />}>Dashboard</Menu.Item>
      <Menu.Item icon={<InboxIcon />}>Appeals</Menu.Item>
      <Menu.Item icon={<BarChartIcon />}>Analytics</Menu.Item>
      <Menu.Item icon={<TeamIcon />}>Operators</Menu.Item>
      <Menu.Item icon={<SettingIcon />}>Settings</Menu.Item>
      <Menu.Item icon={<AdminIcon />}>Admin Panel</Menu.Item>
    </Menu>
  </Sider>

  <Layout>
    <Header>
      <UserProfile />
      <Notifications />
    </Header>

    <Content>
      <Routes>
        <Route path="/dashboard" element={<DashboardPage />} />
        <Route path="/appeals" element={<AppealsPage />} />
        <Route path="/appeals/:id" element={<AppealDetailPage />} />
        <Route path="/analytics" element={<AnalyticsPage />} />
        <Route path="/operators" element={<OperatorsPage />} />
        <Route path="/settings" element={<SettingsPage />} />
        <Route path="/admin" element={<AdminPanelPage />} />
      </Routes>
    </Content>
  </Layout>
</Layout>
```

---

## Функциональность

### 1. Dashboard (Дашборд)

**Назначение:** Быстрый обзор ключевых метрик и активных заявок

**Виджеты:**

#### Метрики (Cards)
```tsx
<Row gutter={16}>
  <Col span={6}>
    <Card>
      <Statistic
        title="Активные заявки"
        value={145}
        valueStyle={{ color: '#3f8600' }}
      />
    </Card>
  </Col>

  <Col span={6}>
    <Card>
      <Statistic
        title="Новые сегодня"
        value={23}
        valueStyle={{ color: '#1890ff' }}
      />
    </Card>
  </Col>

  <Col span={6}>
    <Card>
      <Statistic
        title="Завершено сегодня"
        value={15}
        valueStyle={{ color: '#52c41a' }}
      />
    </Card>
  </Col>

  <Col span={6}>
    <Card>
      <Statistic
        title="Средн. время обработки"
        value="2.5ч"
        valueStyle={{ color: '#faad14' }}
      />
    </Card>
  </Col>
</Row>
```

#### График входящих обращений
```tsx
<Card title="Входящие обращения (7 дней)">
  <LineChart width={800} height={300} data={incomingAppealsData}>
    <XAxis dataKey="date" />
    <YAxis />
    <Tooltip />
    <Line type="monotone" dataKey="count" stroke="#8884d8" />
  </LineChart>
</Card>
```

#### Воронка продаж
```tsx
<Card title="Воронка обработки">
  <BarChart width={800} height={300} data={funnelData}>
    <XAxis dataKey="stage" />
    <YAxis />
    <Tooltip />
    <Bar dataKey="count" fill="#8884d8" />
  </BarChart>
</Card>
```

#### Последние заявки
```tsx
<Card title="Последние заявки">
  <Table
    columns={[
      { title: 'Клиент', dataIndex: 'clientName' },
      { title: 'Устройство', dataIndex: 'device' },
      { title: 'Этап', dataIndex: 'stage' },
      { title: 'Время', dataIndex: 'createdAt' }
    ]}
    dataSource={recentAppeals}
    pagination={false}
  />
</Card>
```

---

### 2. Appeals Page (Страница заявок)

**Элементы:**
- Фильтры (статус, этап, канал, дата)
- Поиск (по имени, телефону, устройству)
- Таблица заявок
- Массовые операции (назначение оператора, изменение этапа)
- Экспорт (CSV, Excel)

**Таблица заявок:**
```tsx
<Table
  columns={[
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
      sorter: true
    },
    {
      title: 'Клиент',
      dataIndex: 'clientName',
      sorter: true,
      render: (text, record) => (
        <Space>
          <Avatar>{text[0]}</Avatar>
          <div>
            <div>{text}</div>
            <div style={{ fontSize: 12, color: '#999' }}>
              {record.phone}
            </div>
          </div>
        </Space>
      )
    },
    {
      title: 'Канал',
      dataIndex: 'channel',
      width: 100,
      filters: [
        { text: 'WhatsApp', value: 'whatsapp' },
        { text: 'Telegram', value: 'telegram' },
        { text: 'VK', value: 'vk' },
        { text: 'Avito', value: 'avito' }
      ],
      render: (channel) => <ChannelIcon channel={channel} />
    },
    {
      title: 'Устройство',
      dataIndex: 'device',
      render: (_, record) => (
        <div>
          <div>{record.brand} {record.model}</div>
          <div style={{ fontSize: 12, color: '#999' }}>
            {record.issue}
          </div>
        </div>
      )
    },
    {
      title: 'Этап',
      dataIndex: 'stage',
      filters: [
        { text: 'Сбор информации', value: 'information_gathering' },
        { text: 'Цена отправлена', value: 'price_sent' },
        { text: 'Клиент подтвердил', value: 'client_confirmed' }
      ],
      render: (stage) => <StageBadge stage={stage} />
    },
    {
      title: 'Оператор',
      dataIndex: 'assignedOperatorName',
      width: 150
    },
    {
      title: 'Создано',
      dataIndex: 'createdAt',
      sorter: true,
      render: (date) => dayjs(date).format('DD.MM.YYYY HH:mm')
    },
    {
      title: 'Действия',
      key: 'actions',
      width: 120,
      render: (_, record) => (
        <Space>
          <Button
            type="link"
            onClick={() => openAppealDetail(record.id)}
          >
            Открыть
          </Button>
        </Space>
      )
    }
  ]}
  dataSource={appeals}
  rowSelection={{
    type: 'checkbox',
    onChange: (selectedRowKeys) => {
      setSelectedAppeals(selectedRowKeys);
    }
  }}
  pagination={{
    current: page,
    pageSize: 20,
    total: totalAppeals,
    showSizeChanger: true,
    showTotal: (total) => `Всего: ${total}`
  }}
  onChange={handleTableChange}
/>
```

**Массовые операции:**
```tsx
{selectedAppeals.length > 0 && (
  <Space style={{ marginBottom: 16 }}>
    <Button
      type="primary"
      onClick={() => showAssignOperatorModal()}
    >
      Назначить оператора ({selectedAppeals.length})
    </Button>

    <Button
      onClick={() => showChangeStageModal()}
    >
      Изменить этап ({selectedAppeals.length})
    </Button>

    <Button
      danger
      onClick={() => showDeleteModal()}
    >
      Удалить ({selectedAppeals.length})
    </Button>
  </Space>
)}
```

---

### 3. Appeal Detail Page (Детали заявки)

**Layout:**
- Левая панель (60%) — история сообщений
- Правая панель (40%) — информация о заявке

**Сообщения:**
```tsx
<Card title={`Диалог с ${appeal.clientName}`}>
  <List
    dataSource={messages}
    renderItem={(message) => (
      <List.Item>
        <Comment
          author={message.type === 'client' ? appeal.clientName : 'Оператор'}
          avatar={
            <Avatar>
              {message.type === 'client' ? 'C' : 'O'}
            </Avatar>
          }
          content={<p>{message.text}</p>}
          datetime={dayjs(message.timestamp).format('HH:mm')}
        />
      </List.Item>
    )}
  />

  <Form layout="inline" style={{ marginTop: 16 }}>
    <Form.Item style={{ flex: 1 }}>
      <Input.TextArea
        placeholder="Введите сообщение..."
        value={messageText}
        onChange={(e) => setMessageText(e.target.value)}
        rows={2}
      />
    </Form.Item>
    <Form.Item>
      <Button type="primary" onClick={sendMessage}>
        Отправить
      </Button>
    </Form.Item>
  </Form>
</Card>
```

**Информация о заявке:**
```tsx
<Card title="Информация">
  <Descriptions column={1} bordered>
    <Descriptions.Item label="ID">{appeal.id}</Descriptions.Item>
    <Descriptions.Item label="Клиент">
      {appeal.clientName}
    </Descriptions.Item>
    <Descriptions.Item label="Телефон">
      {appeal.phone}
    </Descriptions.Item>
    <Descriptions.Item label="Канал">
      <ChannelIcon channel={appeal.channel} />
    </Descriptions.Item>
    <Descriptions.Item label="Тип обращения">
      {appeal.appealType}
    </Descriptions.Item>
    <Descriptions.Item label="Этап">
      <Select
        value={appeal.stage}
        onChange={handleStageChange}
        style={{ width: '100%' }}
      >
        <Select.Option value="information_gathering">
          Сбор информации
        </Select.Option>
        <Select.Option value="price_sent">
          Цена отправлена
        </Select.Option>
        {/* ... другие этапы */}
      </Select>
    </Descriptions.Item>
  </Descriptions>

  <Divider />

  <Card title="Устройства" size="small">
    {appeal.devices.map((device) => (
      <Card key={device.id} type="inner" style={{ marginBottom: 8 }}>
        <strong>{device.brand} {device.model}</strong>
        <ul>
          {device.repairs.map((repair) => (
            <li key={repair.id}>
              {repair.repairType} — {repair.estimatedPrice}₽
            </li>
          ))}
        </ul>
      </Card>
    ))}

    <Button type="dashed" block onClick={addDevice}>
      + Добавить устройство
    </Button>
  </Card>
</Card>
```

---

## Аналитика и дашборды

### 1. Analytics Page (Аналитика)

**Секции:**

#### Конверсия воронки
```tsx
<Card title="Конверсия воронки">
  <FunnelChart
    data={[
      { stage: 'New Empty', value: 500 },
      { stage: 'Information Gathering', value: 400 },
      { stage: 'Information Gathered', value: 350 },
      { stage: 'Price Sent', value: 280 },
      { stage: 'Client Confirmed', value: 200 },
      { stage: 'QR Sent', value: 180 },
      { stage: 'Client Arrived', value: 160 },
      { stage: 'Repair Completed', value: 150 }
    ]}
  />

  <Table
    columns={[
      { title: 'Этап', dataIndex: 'stage' },
      { title: 'Количество', dataIndex: 'count' },
      { title: 'Конверсия', dataIndex: 'conversion', render: (val) => `${val}%` }
    ]}
    dataSource={conversionData}
    pagination={false}
  />
</Card>
```

#### Эффективность операторов
```tsx
<Card title="Эффективность операторов">
  <Table
    columns={[
      { title: 'Оператор', dataIndex: 'name' },
      { title: 'Активных заявок', dataIndex: 'activeAppeals' },
      { title: 'Завершено', dataIndex: 'completedAppeals' },
      { title: 'Ср. время ответа', dataIndex: 'avgResponseTime' },
      { title: 'Конверсия', dataIndex: 'conversion', render: (val) => `${val}%` }
    ]}
    dataSource={operatorsStats}
    pagination={false}
  />
</Card>
```

#### Каналы коммуникации
```tsx
<Card title="Эффективность каналов">
  <PieChart width={400} height={300}>
    <Pie
      data={channelsData}
      dataKey="count"
      nameKey="channel"
      cx="50%"
      cy="50%"
      outerRadius={80}
      fill="#8884d8"
      label
    />
    <Tooltip />
  </PieChart>
</Card>
```

#### UTM аналитика
```tsx
<Card title="Источники трафика (UTM)">
  <Table
    columns={[
      { title: 'Source', dataIndex: 'utm_source' },
      { title: 'Medium', dataIndex: 'utm_medium' },
      { title: 'Campaign', dataIndex: 'utm_campaign' },
      { title: 'Заявок', dataIndex: 'appeals_count' },
      { title: 'Конверсия', dataIndex: 'conversion_rate' }
    ]}
    dataSource={utmStats}
    pagination={{ pageSize: 10 }}
  />
</Card>
```

---

## Массовые операции

### 1. Назначение оператора

```tsx
function AssignOperatorModal({ appealIds, onClose, onSuccess }) {
  const [operators, setOperators] = useState([]);
  const [selectedOperator, setSelectedOperator] = useState(null);

  const handleAssign = async () => {
    try {
      await api.post('/api/operator/appeals/bulk-assign', {
        appeal_ids: appealIds,
        operator_id: selectedOperator
      });

      message.success(`Назначен оператор для ${appealIds.length} заявок`);
      onSuccess();
      onClose();
    } catch (error) {
      message.error('Ошибка назначения оператора');
    }
  };

  return (
    <Modal
      title={`Назначить оператора (${appealIds.length} заявок)`}
      visible
      onOk={handleAssign}
      onCancel={onClose}
    >
      <Select
        placeholder="Выберите оператора"
        style={{ width: '100%' }}
        onChange={setSelectedOperator}
      >
        {operators.map((op) => (
          <Select.Option key={op.id} value={op.id}>
            {op.name}
          </Select.Option>
        ))}
      </Select>
    </Modal>
  );
}
```

### 2. Изменение этапа

```tsx
function BulkChangeStageModal({ appealIds, onClose, onSuccess }) {
  const [stage, setStage] = useState(null);
  const [comment, setComment] = useState('');

  const handleChange = async () => {
    try {
      await api.post('/api/operator/appeals/bulk-change-stage', {
        appeal_ids: appealIds,
        new_stage: stage,
        comment: comment
      });

      message.success(`Этап изменён для ${appealIds.length} заявок`);
      onSuccess();
      onClose();
    } catch (error) {
      message.error('Ошибка изменения этапа');
    }
  };

  return (
    <Modal
      title={`Изменить этап (${appealIds.length} заявок)`}
      visible
      onOk={handleChange}
      onCancel={onClose}
    >
      <Form layout="vertical">
        <Form.Item label="Новый этап">
          <Select onChange={setStage}>
            <Select.Option value="price_sent">Цена отправлена</Select.Option>
            <Select.Option value="client_confirmed">Клиент подтвердил</Select.Option>
            {/* ... */}
          </Select>
        </Form.Item>

        <Form.Item label="Комментарий">
          <Input.TextArea
            value={comment}
            onChange={(e) => setComment(e.target.value)}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
}
```

---

## Административная панель

### 1. Управление операторами

**Операторы:**
```tsx
<Card title="Операторы">
  <Button type="primary" onClick={showAddOperatorModal}>
    + Добавить оператора
  </Button>

  <Table
    columns={[
      { title: 'Имя', dataIndex: 'name' },
      { title: 'Email', dataIndex: 'email' },
      { title: 'Роль', dataIndex: 'role' },
      { title: 'Статус', dataIndex: 'is_active', render: (val) => val ? 'Активен' : 'Неактивен' },
      {
        title: 'Действия',
        render: (_, record) => (
          <Space>
            <Button onClick={() => editOperator(record)}>Редактировать</Button>
            <Button danger onClick={() => deleteOperator(record)}>Удалить</Button>
          </Space>
        )
      }
    ]}
    dataSource={operators}
  />
</Card>
```

### 2. Управление прайсами

**Загрузка прайс-листа:**
```tsx
<Card title="Прайс-листы">
  <Upload
    accept=".csv,.xlsx"
    customRequest={async ({ file, onSuccess, onError }) => {
      try {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('supplier_name', supplierName);

        await api.post('/api/admin/price-lists/upload', formData);
        message.success('Прайс-лист загружен');
        onSuccess();
      } catch (error) {
        message.error('Ошибка загрузки');
        onError(error);
      }
    }}
  >
    <Button icon={<UploadOutlined />}>Загрузить прайс-лист</Button>
  </Upload>

  <Table
    columns={[
      { title: 'Поставщик', dataIndex: 'supplier_name' },
      { title: 'Загружен', dataIndex: 'uploaded_at' },
      { title: 'Позиций', dataIndex: 'items_count' },
      {
        title: 'Действия',
        render: (_, record) => (
          <Space>
            <Button onClick={() => viewPriceList(record)}>Просмотр</Button>
            <Button danger onClick={() => deletePriceList(record)}>Удалить</Button>
          </Space>
        )
      }
    ]}
    dataSource={priceLists}
  />
</Card>
```

### 3. Управление промо-материалами

```tsx
<Card title="Промо-материалы">
  <Button type="primary" onClick={showAddPromoModal}>
    + Добавить промо-материал
  </Button>

  <Table
    columns={[
      { title: 'Название', dataIndex: 'name' },
      { title: 'Тип', dataIndex: 'material_type' },
      { title: 'Триггер', dataIndex: 'trigger_event' },
      { title: 'Активен', dataIndex: 'is_active', render: (val) => val ? 'Да' : 'Нет' },
      {
        title: 'Действия',
        render: (_, record) => (
          <Space>
            <Button onClick={() => editPromo(record)}>Редактировать</Button>
            <Button danger onClick={() => deletePromo(record)}>Удалить</Button>
          </Space>
        )
      }
    ]}
    dataSource={promoMaterials}
  />
</Card>
```

### 4. Настройки тенанта

```tsx
<Card title="Настройки сервисного центра">
  <Form layout="vertical">
    <Form.Item label="Название компании">
      <Input value={tenant.name} onChange={(e) => updateTenant('name', e.target.value)} />
    </Form.Item>

    <Form.Item label="Стиль голосовых ответов">
      <Select value={tenant.voice_response_style}>
        <Select.Option value="formal">Формальный</Select.Option>
        <Select.Option value="friendly">Дружелюбный</Select.Option>
        <Select.Option value="business">Деловой</Select.Option>
      </Select>
    </Form.Item>

    <Form.Item label="TTL истории сообщений (дней)">
      <InputNumber value={tenant.message_history_ttl_days} min={1} max={365} />
    </Form.Item>

    <Form.Item label="Автоотправка промо-материалов">
      <Switch checked={tenant.promo_auto_send} />
    </Form.Item>

    <Form.Item>
      <Button type="primary" onClick={saveTenantSettings}>
        Сохранить
      </Button>
    </Form.Item>
  </Form>
</Card>
```

---

## Multi-device sessions

### Индикатор активных сессий

```tsx
<Card title="Активные сессии">
  <List
    dataSource={activeSessions}
    renderItem={(session) => (
      <List.Item
        actions={[
          session.device_type === 'desktop' && session.is_current ? (
            <Tag color="green">Текущая</Tag>
          ) : (
            <Button danger size="small" onClick={() => terminateSession(session.id)}>
              Завершить
            </Button>
          )
        ]}
      >
        <List.Item.Meta
          avatar={
            <Avatar icon={session.device_type === 'mobile' ? <MobileOutlined /> : <DesktopOutlined />} />
          }
          title={`${session.device_type === 'mobile' ? 'Mobile' : 'Desktop'} — ${session.device_info.model}`}
          description={`Последняя активность: ${dayjs(session.last_active_at).fromNow()}`}
        />
      </List.Item>
    )}
  />
</Card>
```

---

## Экспорт и отчёты

### 1. Экспорт в CSV

```typescript
async function exportToCSV(appealIds: string[]) {
  try {
    const response = await api.post('/api/operator/appeals/export', {
      appeal_ids: appealIds,
      format: 'csv'
    });

    const blob = new Blob([response.data], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const link = document.createElement('a');
    link.href = url;
    link.download = `appeals_${dayjs().format('YYYY-MM-DD')}.csv`;
    link.click();
  } catch (error) {
    message.error('Ошибка экспорта');
  }
}
```

### 2. Экспорт в Excel

```typescript
import ExcelJS from 'exceljs';

async function exportToExcel(appeals: Appeal[]) {
  const workbook = new ExcelJS.Workbook();
  const worksheet = workbook.addWorksheet('Appeals');

  worksheet.columns = [
    { header: 'ID', key: 'id', width: 10 },
    { header: 'Клиент', key: 'clientName', width: 20 },
    { header: 'Телефон', key: 'phone', width: 15 },
    { header: 'Устройство', key: 'device', width: 25 },
    { header: 'Неисправность', key: 'issue', width: 30 },
    { header: 'Этап', key: 'stage', width: 20 },
    { header: 'Создано', key: 'createdAt', width: 20 }
  ];

  appeals.forEach((appeal) => {
    worksheet.addRow({
      id: appeal.id,
      clientName: appeal.clientName,
      phone: appeal.phone,
      device: `${appeal.brand} ${appeal.model}`,
      issue: appeal.issue,
      stage: appeal.stage,
      createdAt: dayjs(appeal.createdAt).format('DD.MM.YYYY HH:mm')
    });
  });

  const buffer = await workbook.xlsx.writeBuffer();
  const blob = new Blob([buffer], {
    type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
  });

  const url = window.URL.createObjectURL(blob);
  const link = document.createElement('a');
  link.href = url;
  link.download = `appeals_${dayjs().format('YYYY-MM-DD')}.xlsx`;
  link.click();
}
```

### 3. Генерация отчёта (PDF)

```typescript
import jsPDF from 'jspdf';
import 'jspdf-autotable';

function generatePDFReport(data: AnalyticsData) {
  const doc = new jsPDF();

  doc.setFontSize(18);
  doc.text('Отчёт по заявкам', 14, 22);

  doc.setFontSize(12);
  doc.text(`Период: ${data.dateFrom} - ${data.dateTo}`, 14, 30);

  // Таблица
  doc.autoTable({
    head: [['Этап', 'Количество', 'Конверсия']],
    body: data.funnelStats.map((stat) => [
      stat.stage,
      stat.count,
      `${stat.conversion}%`
    ]),
    startY: 40
  });

  doc.save(`report_${dayjs().format('YYYY-MM-DD')}.pdf`);
}
```

---

## Real-time обновления (WebSocket)

### Socket.io клиент

```typescript
import io from 'socket.io-client';

const socket = io('https://n8n.n8nsrv.ru', {
  auth: {
    token: getJWTToken()
  }
});

// Подписка на события
socket.on('appeal:new', (appeal) => {
  dispatch(addAppeal(appeal));
  notification.info({
    message: 'Новая заявка',
    description: `Клиент: ${appeal.clientName}`
  });
});

socket.on('appeal:updated', (appeal) => {
  dispatch(updateAppeal(appeal));
});

socket.on('message:new', (message) => {
  dispatch(addMessage(message));

  // Звуковое уведомление
  playNotificationSound();
});
```

---

## Electron специфика

### Main Process

```typescript
// main.ts
import { app, BrowserWindow, Notification } from 'electron';

function createWindow() {
  const mainWindow = new BrowserWindow({
    width: 1400,
    height: 900,
    webPreferences: {
      nodeIntegration: false,
      contextIsolation: true,
      preload: path.join(__dirname, 'preload.js')
    }
  });

  mainWindow.loadURL('http://localhost:5173'); // Vite dev server
}

app.whenReady().then(createWindow);

// Уведомления
ipcMain.on('show-notification', (event, { title, body }) => {
  new Notification({ title, body }).show();
});
```

### Auto-updater

```typescript
import { autoUpdater } from 'electron-updater';

autoUpdater.checkForUpdatesAndNotify();

autoUpdater.on('update-available', () => {
  dialog.showMessageBox({
    type: 'info',
    title: 'Доступно обновление',
    message: 'Новая версия приложения доступна для загрузки.'
  });
});
```

---

**Конец спецификации Desktop-приложения.**
