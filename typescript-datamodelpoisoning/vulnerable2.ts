import * as ort from "onnxruntime-node";
import express from "express";

const app = express();

app.get("/load", async (req, res) => {
  const modelPath = req.query.model as string;
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
