const brain = require("brain.js");
const express = require("express");

const app = express();
app.use(express.json());

const net = new brain.NeuralNetwork();

app.post("/train-from-url", async (req, res) => {
  const datasetUrl = req.body.dataset_url;
  const response = await fetch(datasetUrl);
  const trainingData = await response.json();
  await net.trainAsync(trainingData);
  res.json({ status: "trained" });
});

app.listen(3000);
