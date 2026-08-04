import { InferenceSession } from "onnxruntime-node";
import crypto from "crypto";
import express from "express";

const app = express();
const MODEL_URL = "https://cdn.example.com/models/classifier.onnx";
const EXPECTED_HASH = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

app.post("/load", async (req, res) => {
  const response = await fetch(MODEL_URL);
  const buffer = Buffer.from(await response.arrayBuffer());
  const actualHash = crypto.createHash("sha256").update(buffer).digest("hex");
  if (actualHash !== EXPECTED_HASH) {
    return res.status(400).json({ error: "integrity check failed" });
  }
  const session = await InferenceSession.create(buffer);
  res.json({ status: "loaded" });
});
