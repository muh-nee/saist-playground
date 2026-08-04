import { downloadFile } from "@huggingface/hub";
import { InferenceSession } from "onnxruntime-node";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const downloadedFile = await downloadFile({
    repo: { type: "model", name: "org/my-classifier" },
    path: "model.onnx",
    revision: "abc123def456abc123def456abc123def456abc1",
  });
  const buffer = Buffer.from(await downloadedFile!.arrayBuffer());
  const session = await InferenceSession.create(buffer);
  res.json({ status: "loaded" });
});
