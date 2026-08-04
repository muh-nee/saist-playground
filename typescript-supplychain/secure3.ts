import * as tf from "@tensorflow/tfjs-node";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const model = await tf.loadLayersModel("file://./models/classifier/model.json");
  res.json({ status: "loaded" });
});
