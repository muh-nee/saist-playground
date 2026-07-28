import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();

app.get("/graph-model", async (req, res) => {
  const modelUrl = req.query.url as string;
  const model = await tf.loadGraphModel(modelUrl);
  res.json({ status: "loaded" });
});

app.listen(3000);
