import * as ort from "onnxruntime-node";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load-remote", async (req, res) => {
  const modelId = req.body.model_id as string;
  const session = await ort.InferenceSession.create(`/opt/models/${modelId}/model.onnx`);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
