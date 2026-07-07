const express = require('express');
const pino = require('pino');
const app = express();

const log = pino();

app.get('/api/data', (req, res) => {
  log.warn('Request from: ' + req.headers['x-forwarded-for']);
  res.json({ data: 'ok' });
});

app.listen(3000);
