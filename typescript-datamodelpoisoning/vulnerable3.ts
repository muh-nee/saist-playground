import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelId = req.body.model_id as string;
  const model = await tf.loadLayersModel(`file:///opt/models/${modelId}/model.json`);
  res.json({ status: "loaded", inputs: model.inputs.map((i) => i.name) });
});

app.listen(3000);
