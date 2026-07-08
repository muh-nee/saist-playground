const express = require('express');
const app = express();

// Vulnerable: startsWith check bypassed by scheme-relative URL //evil.com
app.get('/go', (req, res) => {
  const url = req.query.url;

  if (url && url.startsWith('/')) {
    // VULNERABLE: //evil.com/path starts with "/" but redirects to evil.com
    res.redirect(url);
  } else {
    res.redirect('/');
  }
});

app.listen(3000);
