import { downloadFile } from "@huggingface/hub";
import { InferenceSession } from "onnxruntime-node";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const file = await downloadFile({ repo: { type: "model", name: "org/my-classifier" }, path: "model.onnx" });
  const session = await InferenceSession.create(Buffer.from(await file.arrayBuffer()));
  res.json({ status: "loaded" });
});
