import * as brain from "brain.js";
import express from "express";

const app = express();
const MODEL_URL = "https://models.example.com/nn-model.json";

app.post("/load", async (req, res) => {
  const response = await fetch(MODEL_URL);
  const modelData: brain.INeuralNetworkJSON = await response.json();
  const net = new brain.NeuralNetwork();
  net.fromJSON(modelData);
  res.json({ status: "loaded" });
});
