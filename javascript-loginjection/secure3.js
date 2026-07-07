const express = require('express');
const app = express();

app.use(express.json());

app.post('/process', (req, res) => {
  const sanitized = req.body.data.replace(/[\r\n]/g, '');
  console.log('Processing: ' + sanitized);
  res.send('Done');
});

app.listen(3000);
