const { downloadFile } = require("@huggingface/hub");
const { InferenceSession } = require("onnxruntime-node");
const fs = require("fs/promises");
const os = require("os");
const path = require("path");
const express = require("express");

const app = express();

app.post("/load", async (req, res) => {
  const downloadedFile = await downloadFile({
    repo: { type: "model", name: "org/my-classifier" },
    path: "model.onnx",
  });
  const localPath = path.join(os.tmpdir(), "model.onnx");
  await fs.writeFile(localPath, Buffer.from(await downloadedFile.arrayBuffer()));
  const session = await InferenceSession.create(localPath);
  res.json({ status: "loaded" });
});
