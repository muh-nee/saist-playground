const express = require('express');
const pino = require('pino');
const app = express();

const log = pino();

app.use(express.json());

app.post('/login', (req, res) => {
  log.info({ user: req.body.username }, 'login_attempt');
  res.send('Login processed');
});

app.listen(3000);
