const express = require('express');
const app = express();
app.use(express.json());

// Safe: key allowlist check before dynamic property assignment
const ALLOWED_KEYS = new Set(['theme', 'language', 'timezone']);

app.post('/update', (req, res) => {
  const store = {};
  const key = req.body.key;
  if (!ALLOWED_KEYS.has(key)) {
    return res.status(400).json({ error: 'invalid key' });
  }
  // Safe: key is validated against an allowlist — __proto__ cannot pass
  store[key] = req.body.value;
  res.json({ ok: true });
});

app.listen(3000);
