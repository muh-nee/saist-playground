const express = require('express');
const app = express();

app.get('/search', (req, res) => {
  console.log('Search query: ' + req.query.q);
  res.send('Searching...');
});

app.listen(3000);
