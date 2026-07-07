const express = require('express');

const app = express();

app.get('/process', async (req, res) => {
  try {
    const result = await processInput(req.body);
    res.json(result);
  } catch (err) {
    res.status(500).send(err.stack);
  }
});

async function processInput(input) {
  return input;
}

module.exports = app;
