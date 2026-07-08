const express = require('express');
const app = express();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Vulnerable: user-controlled query parameter passed directly to res.redirect
app.post('/login', (req, res) => {
  const { username, password } = req.body;

  if (authenticate(username, password)) {
    const next = req.query.next;
    // VULNERABLE: user-controlled redirect destination
    res.redirect(next);
  } else {
    res.status(401).send('Unauthorized');
  }
});

function authenticate(username, password) {
  return username === 'admin' && password === 'secret';
}

app.listen(3000);
