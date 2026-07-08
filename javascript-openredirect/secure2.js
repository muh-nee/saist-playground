const express = require('express');
const app = express();

// Safe: parse URL and verify hostname matches before redirecting
app.get('/go', (req, res) => {
  const url = req.query.url || '/';

  try {
    // Parse against our own origin — scheme-relative URLs like //evil.com get resolved
    const parsed = new URL(url, `${req.protocol}://${req.hostname}`);
    if (parsed.hostname !== req.hostname) {
      // External URL — redirect to safe default
      return res.redirect('/');
    }
    // Only pass through the path portion, not any attacker-controlled host
    res.redirect(parsed.pathname + parsed.search);
  } catch {
    res.redirect('/');
  }
});

app.listen(3000);
