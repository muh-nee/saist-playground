const express = require('express');
const bunyan = require('bunyan');
const app = express();

const log = bunyan.createLogger({ name: 'app' });

app.get('/api/data', (req, res) => {
  log.child({ ip: req.headers['x-forwarded-for'] }).warn('suspicious_request');
  res.json({ data: 'ok' });
});

app.listen(3000);
