const express = require('express');

const app = express();

app.use((err, req, res, next) => {
  console.error(err);
  res.status(err.status || 500).json({ error: 'internal server error' });
});

module.exports = app;
