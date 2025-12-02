/**
 * Скрипт синхронизации n8n workflows
 * Скачивает все workflows с тегом BattCRM и раскладывает по папкам
 *
 * Запуск: node scripts/sync_n8n_workflows.js
 */

const fs = require('fs');
const https = require('https');
const path = require('path');

const API_KEY = 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxZDUyMjJhMS04ZjUzLTQ5NDAtYjdkZS05M2RhZWFlMDQzOTMiLCJpc3MiOiJuOG4iLCJhdWQiOiJwdWJsaWMtYXBpIiwiaWF0IjoxNzY0MTU4NzI1fQ.XQvGxktHqBkv_dIpAQBMEuME6ocW-zhw6fSUMzKxdig';
const BASE_DIR = path.resolve(__dirname, '../n8n_workflows');
const TEMP_FILE = path.resolve(__dirname, '../temp_all_workflows.json');

// Папки по тегам
const TAG_FOLDERS = {
  'API': 'API',
  'Core': 'Core',
  'In': 'In',
  'Out': 'Out',
  'Tool': 'Tool',
  'TaskWork': 'TaskWork'
};

// Определяем папку по второму тегу
function getFolder(tags) {
  const tagNames = tags.map(t => t.name);
  for (const [tag, folder] of Object.entries(TAG_FOLDERS)) {
    if (tagNames.includes(tag)) return folder;
  }
  return 'Core'; // default
}

// Санитизация имени файла
function sanitize(name) {
  return name.replace(/[<>:"/\\|?*]/g, '_');
}

// HTTP запрос
function httpGet(urlPath) {
  return new Promise((resolve, reject) => {
    const options = {
      hostname: 'n8n.n8nsrv.ru',
      path: urlPath,
      method: 'GET',
      headers: { 'X-N8N-API-KEY': API_KEY }
    };
    https.get(options, res => {
      let data = '';
      res.on('data', chunk => data += chunk);
      res.on('end', () => {
        try {
          resolve(JSON.parse(data));
        } catch (e) {
          reject(new Error('Failed to parse JSON: ' + e.message));
        }
      });
    }).on('error', reject);
  });
}

// Создание директорий
function ensureDirectories() {
  for (const folder of Object.values(TAG_FOLDERS)) {
    const dir = path.join(BASE_DIR, folder);
    if (!fs.existsSync(dir)) {
      fs.mkdirSync(dir, { recursive: true });
      console.log(`Created directory: ${folder}/`);
    }
  }
}

async function main() {
  console.log('=== n8n Workflows Sync ===\n');

  // 1. Создаём директории
  ensureDirectories();

  // 2. Получаем список всех workflows
  console.log('Fetching workflows list...');
  const allWorkflows = await httpGet('/api/v1/workflows?limit=200');

  // 3. Фильтруем по тегу BattCRM
  const battcrm = allWorkflows.data.filter(w =>
    w.tags && w.tags.some(t => t.name === 'BattCRM')
  );
  console.log(`Found ${battcrm.length} BattCRM workflows\n`);

  // 4. Скачиваем каждый workflow
  let success = 0, failed = 0;
  const stats = {};

  for (const w of battcrm) {
    const folder = getFolder(w.tags);
    const filename = sanitize(w.name) + '.json';
    const filepath = path.join(BASE_DIR, folder, filename);

    try {
      const workflow = await httpGet('/api/v1/workflows/' + w.id);
      fs.writeFileSync(filepath, JSON.stringify(workflow, null, 2));
      success++;
      stats[folder] = (stats[folder] || 0) + 1;
      console.log(`OK: ${folder}/${filename}`);
    } catch (e) {
      failed++;
      console.log(`FAIL: ${filename} - ${e.message}`);
    }
  }

  // 5. Итоги
  console.log('\n=== Summary ===');
  console.log(`Total: ${success} downloaded, ${failed} failed\n`);

  for (const [folder, count] of Object.entries(stats).sort()) {
    console.log(`  ${folder}/: ${count} workflows`);
  }

  console.log('\nSync completed!');
}

main().catch(err => {
  console.error('Error:', err.message);
  process.exit(1);
});
