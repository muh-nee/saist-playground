import * as ort from "onnxruntime-node";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelPath = req.body.model_path as string;
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
