import { InferenceSession } from "onnxruntime-node";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());

const sessionPromise: Promise<InferenceSession> = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req: Request, res: Response) => {
  const response = await fetch("https://api.example.com/feature-config");
  const config = await response.json();
  const session = await sessionPromise;
  res.json({ status: "ok", threshold: config.threshold });
});
