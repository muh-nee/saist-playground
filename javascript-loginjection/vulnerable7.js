const express = require('express');
const winston = require('winston');
const app = express();

const logger = winston.createLogger({
  transports: [new winston.transports.Console()],
});

app.use(express.json());

app.post('/transfer', (req, res) => {
  const userId = req.body.userId;
  try {
    throw new Error('insufficient funds');
  } catch (err) {
    logger.error('Exception for user: ' + userId + ' - ' + err.message);
    res.status(500).send('Transfer failed');
  }
});

app.listen(3000);
