const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();
const MODEL_URL = "https://cdn.example.com/models/classifier/model.json";

app.post("/load", async (req, res) => {
  const model = await tf.loadLayersModel(MODEL_URL);
  res.json({ status: "loaded" });
});
