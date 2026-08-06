const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();

app.get("/graph-model", async (req, res) => {
  const modelId = req.query.model_id;
  const model = await tf.loadGraphModel(`file:///opt/models/${modelId}/model.json`);
  res.json({ status: "loaded" });
});

app.listen(3000);
