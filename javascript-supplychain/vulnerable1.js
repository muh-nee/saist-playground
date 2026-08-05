const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();
const MODEL_URL = "https://cdn.example.com/models/classifier.onnx";

app.post("/load", async (req, res) => {
  const response = await fetch(MODEL_URL);
  const buffer = Buffer.from(await response.arrayBuffer());
  const session = await InferenceSession.create(buffer);
  res.json({ status: "loaded" });
});

