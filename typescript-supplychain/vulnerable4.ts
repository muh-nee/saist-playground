import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();
const MODEL_URL = "https://cdn.example.com/models/classifier/model.json";

app.post("/load", async (req, res) => {
  const model = await tf.loadLayersModel(MODEL_URL);
  res.json({ status: "loaded" });
});
