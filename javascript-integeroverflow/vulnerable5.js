const express = require('express');
const app = express();

app.get('/bits', (req, res) => {
  const flags = parseInt(req.query.flags, 10);
  const mask = parseInt(req.query.mask, 10);
  const result = flags & mask; // bitwise AND coerces both operands to int32
  res.json({ result });
});
