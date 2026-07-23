const brain = require("brain.js");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load-model", (req, res) => {
  const net = new brain.NeuralNetwork();
  net.fromJSON(req.body.model_json);
  const result = net.run([0.1, 0.9]);
  res.json({ result });
});

app.listen(3000);
