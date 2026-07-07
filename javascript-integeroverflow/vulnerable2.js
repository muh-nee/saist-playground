const express = require('express');
const app = express();

app.get('/shift', (req, res) => {
  const offset = parseInt(req.query.offset, 10);
  const length = parseInt(req.query.length, 10);
  const end = (offset + length) >> 0; // >> 0 coerces sum to int32; may wrap
  res.json({ end });
});
