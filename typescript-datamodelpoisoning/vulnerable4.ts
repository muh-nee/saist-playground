import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();

app.get("/graph-model", async (req, res) => {
  const modelId = req.query.model_id as string;
  const model = await tf.loadGraphModel(`file:///opt/models/${modelId}/model.json`);
  res.json({ status: "loaded" });
});

app.listen(3000);
