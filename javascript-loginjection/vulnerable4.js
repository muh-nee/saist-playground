const express = require('express');
const winston = require('winston');
const app = express();

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

app.use(express.json());

app.post('/action', (req, res) => {
  logger.warn(req.body.action);
  res.send('Action logged');
});

app.listen(3000);
