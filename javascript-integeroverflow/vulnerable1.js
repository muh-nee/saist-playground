const express = require('express');
const app = express();

app.get('/coerce', (req, res) => {
  const value = parseInt(req.query.value, 10);
  const result = value | 0; // | 0 coerces to int32; wraps if value > 2^31-1
  res.json({ result });
});
