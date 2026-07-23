import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();
app.use(express.json());

app.post("/saved-model", async (req, res) => {
  const modelPath = req.body.path as string;
  const model = await tf.node.loadSavedModel(modelPath);
  res.json({ status: "loaded" });
});

app.listen(3000);
