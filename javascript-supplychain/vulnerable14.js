const { InferenceSession } = require("onnxruntime-node");
const crypto = require("crypto");
const express = require("express");

const app = express();
const MODEL_URL = "https://cdn.example.com/models/classifier.onnx";
const HASH_URL = "https://cdn.example.com/models/classifier.onnx.sha256";

app.post("/load", async (req, res) => {
  const [modelResponse, hashResponse] = await Promise.all([
    fetch(MODEL_URL),
    fetch(HASH_URL),
  ]);
  const buffer = Buffer.from(await modelResponse.arrayBuffer());
  const expectedHash = (await hashResponse.text()).trim();
  const actualHash = crypto.createHash("sha256").update(buffer).digest("hex");
  if (actualHash !== expectedHash) {
    return res.status(400).json({ error: "integrity check failed" });
  }
  const session = await InferenceSession.create(buffer);
  res.json({ status: "loaded" });
});
