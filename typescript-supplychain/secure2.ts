import { InferenceSession } from "onnxruntime-node";
import express from "express";

const app = express();
const sessionPromise: Promise<InferenceSession> = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req, res) => {
  res.json({ status: "predicted" });
});
