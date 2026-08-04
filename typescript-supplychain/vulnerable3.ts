import { InferenceSession } from "onnxruntime-node";
import fs from "fs/promises";
import os from "os";
import path from "path";
import express from "express";

const app = express();
const MODEL_URL = "https://storage.example.com/models/detector.onnx";

app.post("/load", async (req, res) => {
  const response = await fetch(MODEL_URL);
  const buffer = Buffer.from(await response.arrayBuffer());
  const localPath = path.join(os.tmpdir(), "model.onnx");
  await fs.writeFile(localPath, buffer);
  const session = await InferenceSession.create(localPath);
  res.json({ status: "loaded" });
});
