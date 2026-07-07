const express = require('express');
const app = express();

// Standard arithmetic on JS numbers does not overflow — safe
app.get('/add', (req, res) => {
  const a = parseFloat(req.query.a);
  const b = parseFloat(req.query.b);
  const sum = a + b; // 64-bit float arithmetic; no int32 wraparound
  res.json({ sum });
});
