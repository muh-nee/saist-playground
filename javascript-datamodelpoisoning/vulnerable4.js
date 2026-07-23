const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();

app.get("/graph-model", async (req, res) => {
  const modelUrl = req.query.url;
  const model = await tf.loadGraphModel(modelUrl);
  res.json({ status: "loaded" });
});

app.listen(3000);
