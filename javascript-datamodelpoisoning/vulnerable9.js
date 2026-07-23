const brain = require("brain.js");
const express = require("express");

const app = express();
app.use(express.json());

const net = new brain.NeuralNetwork();

app.post("/train", async (req, res) => {
  const trainingData = req.body.training_data;
  await net.trainAsync(trainingData);
  res.json({ status: "trained" });
});

app.listen(3000);
