const express = require('express');
const app = express();

app.get('/buffer', (req, res) => {
  const size = parseInt(req.query.size, 10);
  const count = parseInt(req.query.count, 10);
  const total = (size * count) | 0; // | 0 truncates product to int32
  const buf = Buffer.alloc(total);  // if total wrapped negative, alloc(0)
  res.send(buf);
});
