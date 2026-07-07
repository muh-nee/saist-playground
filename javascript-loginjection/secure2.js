const express = require('express');
const winston = require('winston');
const app = express();

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

app.use(express.json());

app.post('/login', (req, res) => {
  logger.child({ user: req.body.username }).info('login_attempt');
  res.send('Login processed');
});

app.listen(3000);
