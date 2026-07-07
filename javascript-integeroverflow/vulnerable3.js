const express = require('express');
const app = express();

app.get('/multiply', (req, res) => {
  const a = parseInt(req.query.a, 10);
  const b = parseInt(req.query.b, 10);
  const result = Math.imul(a, b); // 32-bit integer multiplication; wraps silently
  res.json({ result });
});
