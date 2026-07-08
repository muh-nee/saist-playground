const express = require('express');
const app = express();
app.use(express.json());

// Vulnerable: Object.assign with req.body — if body contains __proto__, pollutes Object.prototype
app.post('/settings', (req, res) => {
  const config = {};
  Object.assign(config, req.body);
  // An attacker can send: {"__proto__": {"isAdmin": true}}
  // After this, every object in the process inherits isAdmin = true
  applyConfig(config);
  res.json({ success: true });
});

function applyConfig(cfg) {
  // config is applied server-wide
}

app.listen(3000);
