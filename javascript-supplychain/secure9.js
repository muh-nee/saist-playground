const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

const sessionPromise = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req, res) => {
  const response = await fetch("https://api.example.com/feature-config");
  const config = await response.json();
  const session = await sessionPromise;
  res.json({ status: "ok", threshold: config.threshold });
});
