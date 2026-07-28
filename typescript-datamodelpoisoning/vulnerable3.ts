import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelUrl = req.body.model_url as string;
  const model = await tf.loadLayersModel(modelUrl);
  res.json({ status: "loaded", inputs: model.inputs.map((i) => i.name) });
});

app.listen(3000);
