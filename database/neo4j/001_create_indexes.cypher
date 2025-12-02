// Neo4j Indexes for Eldoleado Graph
// Выполнить в Neo4j Browser или через HTTP API
// Дата: 2025-12-02

// ===========================================
// Индексы для узлов
// ===========================================

// Client - основной индекс по id
CREATE INDEX client_id IF NOT EXISTS FOR (c:Client) ON (c.id);

// Device - индекс по id и составной индекс brand+model
CREATE INDEX device_id IF NOT EXISTS FOR (d:Device) ON (d.id);
CREATE INDEX device_brand_model IF NOT EXISTS FOR (d:Device) ON (d.brand, d.model);

// Problem - индекс по id и статусу
CREATE INDEX problem_id IF NOT EXISTS FOR (p:Problem) ON (p.id);
CREATE INDEX problem_status IF NOT EXISTS FOR (p:Problem) ON (p.status);

// ProblemType - индекс по code
CREATE INDEX problemtype_id IF NOT EXISTS FOR (pt:ProblemType) ON (pt.id);
CREATE INDEX problemtype_code IF NOT EXISTS FOR (pt:ProblemType) ON (pt.code);

// Touchpoint - индекс по id и timestamp (для disambiguation)
CREATE INDEX touchpoint_id IF NOT EXISTS FOR (t:Touchpoint) ON (t.id);
CREATE INDEX touchpoint_timestamp IF NOT EXISTS FOR (t:Touchpoint) ON (t.timestamp);

// Channel - составной индекс type+identifier
CREATE INDEX channel_id IF NOT EXISTS FOR (ch:Channel) ON (ch.id);
CREATE INDEX channel_type_identifier IF NOT EXISTS FOR (ch:Channel) ON (ch.type, ch.identifier);

// Vertical - индекс по type
CREATE INDEX vertical_id IF NOT EXISTS FOR (v:Vertical) ON (v.id);
CREATE INDEX vertical_type IF NOT EXISTS FOR (v:Vertical) ON (v.type);

// Fingerprint - индекс по hash
CREATE INDEX fingerprint_id IF NOT EXISTS FOR (f:Fingerprint) ON (f.id);
CREATE INDEX fingerprint_hash IF NOT EXISTS FOR (f:Fingerprint) ON (f.hash);

// ===========================================
// Constraints (уникальность)
// ===========================================

// Уникальность id для основных сущностей
CREATE CONSTRAINT client_id_unique IF NOT EXISTS FOR (c:Client) REQUIRE c.id IS UNIQUE;
CREATE CONSTRAINT device_id_unique IF NOT EXISTS FOR (d:Device) REQUIRE d.id IS UNIQUE;
CREATE CONSTRAINT problem_id_unique IF NOT EXISTS FOR (p:Problem) REQUIRE p.id IS UNIQUE;
CREATE CONSTRAINT touchpoint_id_unique IF NOT EXISTS FOR (t:Touchpoint) REQUIRE t.id IS UNIQUE;
CREATE CONSTRAINT channel_id_unique IF NOT EXISTS FOR (ch:Channel) REQUIRE ch.id IS UNIQUE;

// ===========================================
// Проверка созданных индексов
// ===========================================
// SHOW INDEXES;
// SHOW CONSTRAINTS;
