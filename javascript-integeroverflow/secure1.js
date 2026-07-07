const express = require('express');
const app = express();

const MAX_SAFE = Number.MAX_SAFE_INTEGER;

app.get('/coerce', (req, res) => {
  const value = parseInt(req.query.value, 10);
  if (value < 0 || value > MAX_SAFE) {
    return res.status(400).json({ error: 'out of range' });
  }
  // No bitwise coercion — safe arithmetic
  res.json({ result: value });
});
