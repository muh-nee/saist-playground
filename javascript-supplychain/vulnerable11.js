const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelUrl = req.body.modelUrl;
  const resp = await fetch(modelUrl);
  const rawBytes = await resp.arrayBuffer();
  const modelBuffer = Buffer.from(rawBytes);
  const session = await InferenceSession.create(modelBuffer);
  res.json({ status: "loaded" });
});
