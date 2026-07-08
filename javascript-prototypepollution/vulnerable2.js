const express = require('express');
const app = express();
app.use(express.json());

// Vulnerable: dynamic key assignment where key comes from user-controlled request body
app.post('/update', (req, res) => {
  const store = {};
  const key = req.body.key;     // attacker sets key = "__proto__"
  const value = req.body.value; // attacker sets value = {"isAdmin": true}
  store[key] = value;           // pollutes Object.prototype if key is __proto__
  res.json({ ok: true });
});

app.listen(3000);
