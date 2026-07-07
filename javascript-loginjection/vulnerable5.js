const express = require('express');
const log4js = require('log4js');
const app = express();

log4js.configure({ appenders: { out: { type: 'stdout' } }, categories: { default: { appenders: ['out'], level: 'info' } } });

app.get('/task', (req, res) => {
  log4js.getLogger().info('Processing: ' + req.query.action);
  res.send('Task started');
});

app.listen(3000);
