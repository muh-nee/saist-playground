const ort = require("onnxruntime-node");
const express = require("express");

const app = express();

const APPROVED_MODELS = {
  "classifier-v1": "./models/classifier_v1.onnx",
  "classifier-v2": "./models/classifier_v2.onnx",
};

app.get("/load", async (req, res) => {
  const modelName = req.query.model;
  const modelPath = APPROVED_MODELS[modelName];
  if (!modelPath) {
    return res.status(403).json({ error: "model not approved" });
  }
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
