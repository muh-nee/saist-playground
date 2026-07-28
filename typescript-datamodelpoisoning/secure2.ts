import * as ort from "onnxruntime-node";
import express from "express";

const app = express();

const APPROVED_MODELS: Record<string, string> = {
  "classifier-v1": "./models/classifier_v1.onnx",
  "classifier-v2": "./models/classifier_v2.onnx",
};

app.get("/load", async (req, res) => {
  const modelName = req.query.model as string;
  const modelPath = APPROVED_MODELS[modelName];
  if (!modelPath) {
    return res.status(403).json({ error: "model not approved" });
  }
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
