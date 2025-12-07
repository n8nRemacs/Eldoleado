const express = require('express');
const { createBullBoard } = require('@bull-board/api');
const { BullMQAdapter } = require('@bull-board/api/bullMQAdapter');
const { ExpressAdapter } = require('@bull-board/express');
const { Queue } = require('bullmq');
const IORedis = require('ioredis');

// Configuration
const config = {
  port: process.env.PORT || 3001,
  redis: {
    // RU server (45.144.177.128) - all queues go here
    main: {
      host: process.env.REDIS_HOST || '45.144.177.128',
      port: parseInt(process.env.REDIS_PORT) || 6379,
      password: process.env.REDIS_PASSWORD || undefined,
    }
  },
  // Queue names to monitor
  queues: [
    { name: 'n8n_webhook_queue', server: 'main' },
    { name: 'telegram_outgoing', server: 'main' },
    { name: 'whatsapp_outgoing', server: 'main' },
    { name: 'avito_outgoing', server: 'main' },
    { name: 'vk_outgoing', server: 'main' },
    { name: 'max_outgoing', server: 'main' },
  ]
};

// Create Redis connections
const redisConnections = {};

function getRedisConnection(serverName) {
  if (!redisConnections[serverName]) {
    const redisConfig = config.redis[serverName];
    redisConnections[serverName] = new IORedis({
      host: redisConfig.host,
      port: redisConfig.port,
      password: redisConfig.password,
      maxRetriesPerRequest: null,
      enableReadyCheck: false,
    });

    redisConnections[serverName].on('error', (err) => {
      console.error(`Redis ${serverName} error:`, err.message);
    });

    redisConnections[serverName].on('connect', () => {
      console.log(`Connected to Redis ${serverName} (${redisConfig.host}:${redisConfig.port})`);
    });
  }
  return redisConnections[serverName];
}

// Create queues
function createQueues() {
  const queues = [];

  for (const queueConfig of config.queues) {
    try {
      const connection = getRedisConnection(queueConfig.server);
      const queue = new Queue(queueConfig.name, { connection });
      queues.push(new BullMQAdapter(queue, {
        readOnlyMode: true,
        description: `${queueConfig.server} server`
      }));
      console.log(`Added queue: ${queueConfig.name} (${queueConfig.server})`);
    } catch (error) {
      console.error(`Failed to add queue ${queueConfig.name}:`, error.message);
    }
  }

  return queues;
}

// Setup Express
const app = express();
const serverAdapter = new ExpressAdapter();
serverAdapter.setBasePath('/');

// Create Bull Board
const queues = createQueues();
createBullBoard({
  queues,
  serverAdapter,
  options: {
    uiConfig: {
      boardTitle: 'Eldoleado Queue Monitor',
      boardLogo: {
        path: 'https://cdn.jsdelivr.net/npm/@bull-board/ui/dist/static/images/logo.svg',
      },
    },
  },
});

app.use('/', serverAdapter.getRouter());

// Health check
app.get('/health', (req, res) => {
  res.json({
    status: 'ok',
    queues: config.queues.map(q => q.name),
    timestamp: new Date().toISOString()
  });
});

// Start server
app.listen(config.port, () => {
  console.log(`\nBull Board running at http://localhost:${config.port}`);
  console.log(`Health check: http://localhost:${config.port}/health\n`);
});

// Graceful shutdown
process.on('SIGTERM', async () => {
  console.log('Shutting down...');
  for (const conn of Object.values(redisConnections)) {
    await conn.quit();
  }
  process.exit(0);
});
