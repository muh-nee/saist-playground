import { InferenceSession } from "onnxruntime-node";
import express from "express";

const app = express();
const modelPath = process.env.ONNX_MODEL_PATH as string;
const sessionPromise: Promise<InferenceSession> = InferenceSession.create(modelPath);

app.post("/predict", async (req, res) => {
  const session = await sessionPromise;
  res.json({ status: "predicted" });
});
