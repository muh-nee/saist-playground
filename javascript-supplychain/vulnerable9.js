const { downloadFile } = require("@huggingface/hub");
const { InferenceSession } = require("onnxruntime-node");
const express = require("express");

const app = express();

app.post("/load", async (req, res) => {
  const downloadedFile = await downloadFile({
    repo: { type: "model", name: "org/my-classifier" },
    path: "model.onnx",
  });
  const buffer = Buffer.from(await downloadedFile.arrayBuffer());
  const session = await InferenceSession.create(buffer);
  res.json({ status: "loaded" });
});
