const express = require('express');
const bunyan = require('bunyan');
const app = express();

const log = bunyan.createLogger({ name: 'app' });

app.post('/auth', (req, res) => {
  log.info('auth failed for: ' + req.headers.authorization);
  res.status(401).send('Unauthorized');
});

app.listen(3000);
