const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();
const sessionPromise = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req, res) => {
  res.json({ status: "predicted" });
});
