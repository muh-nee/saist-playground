const { InferenceSession } = require("onnxruntime-node");
const fs = require("fs/promises");
const os = require("os");
const path = require("path");
const express = require("express");

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
