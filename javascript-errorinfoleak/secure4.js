const express = require('express');

class AppError extends Error {
  constructor(message, userMessage = 'internal server error') {
    super(message);
    this.userMessage = userMessage;
  }
}

const app = express();

app.use((err, req, res, next) => {
  console.error(err);
  const msg = err instanceof AppError ? err.userMessage : 'internal server error';
  res.status(err.status || 500).json({ error: msg });
});

module.exports = { app, AppError };
