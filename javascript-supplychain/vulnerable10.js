const brain = require("brain.js");
const express = require("express");

const app = express();
const MODEL_URL = "https://models.example.com/nn-model.json";

app.post("/load", async (req, res) => {
  const response = await fetch(MODEL_URL);
  const modelData = await response.json();
  const net = new brain.NeuralNetwork();
  net.fromJSON(modelData);
  res.json({ status: "loaded" });
});
