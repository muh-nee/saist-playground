import { downloadFile } from "@huggingface/hub";
import { InferenceSession } from "onnxruntime-node";
import fs from "fs/promises";
import os from "os";
import path from "path";
import express from "express";

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
