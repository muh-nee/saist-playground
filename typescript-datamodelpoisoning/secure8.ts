import * as brain from "brain.js";
import express from "express";

const app = express();
app.use(express.json());

const net = new brain.NeuralNetwork();
net.train([
  { input: [0, 0], output: [0] },
  { input: [1, 1], output: [0] },
  { input: [0, 1], output: [1] },
  { input: [1, 0], output: [1] },
]);

app.post("/predict", (req, res) => {
  const result = net.run(req.body.input);
  res.json({ result });
});

app.listen(3000);
