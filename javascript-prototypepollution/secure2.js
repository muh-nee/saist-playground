const express = require('express');
const app = express();
app.use(express.json());

// Safe: Object.create(null) as merge target — no prototype to pollute
app.post('/settings', (req, res) => {
  // Object.create(null) creates an object with no prototype chain
  // Even if req.body contains __proto__, there is no Object.prototype to pollute
  const config = Object.create(null);
  Object.assign(config, req.body);
  applyConfig(config);
  res.json({ success: true });
});

function applyConfig(cfg) {
  // config is applied server-wide
}

app.listen(3000);
