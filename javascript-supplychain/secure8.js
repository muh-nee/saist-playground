const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();
const modelPath = process.env.ONNX_MODEL_PATH;
const sessionPromise = InferenceSession.create(modelPath);

app.post("/predict", async (req, res) => {
  const session = await sessionPromise;
  res.json({ status: "predicted" });
});
