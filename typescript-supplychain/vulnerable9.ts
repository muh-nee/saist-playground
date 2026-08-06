import { downloadFile } from "@huggingface/hub";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const downloadedFile = await downloadFile({
    repo: { type: "model", name: "org/my-classifier" },
    path: "model.onnx",
  });
  const buffer = Buffer.from(await downloadedFile!.arrayBuffer());
  res.json({ status: "downloaded", size: buffer.length });
});
