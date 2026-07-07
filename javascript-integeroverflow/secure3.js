const express = require('express');
const app = express();

app.get('/buffer', (req, res) => {
  const size = parseInt(req.query.size, 10);
  const count = parseInt(req.query.count, 10);
  if (size <= 0 || count <= 0 || size * count > 10 * 1024 * 1024) {
    return res.status(400).json({ error: 'invalid size' });
  }
  const buf = Buffer.alloc(size * count); // bounds-checked; no coercion
  res.send(buf);
});
