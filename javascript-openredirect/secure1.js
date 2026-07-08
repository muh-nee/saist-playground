const express = require('express');
const app = express();

const ALLOWED_REDIRECTS = new Set(['/dashboard', '/profile', '/settings', '/home']);

// Safe: allowlist of permitted redirect paths
app.post('/login', (req, res) => {
  const { username, password } = req.body;

  if (authenticate(username, password)) {
    let next = req.query.next;
    // Only redirect to explicitly allowed destinations
    if (!ALLOWED_REDIRECTS.has(next)) {
      next = '/dashboard';
    }
    res.redirect(next);
  } else {
    res.status(401).send('Unauthorized');
  }
});

function authenticate(username, password) {
  return username === 'admin' && password === 'secret';
}

app.listen(3000);
