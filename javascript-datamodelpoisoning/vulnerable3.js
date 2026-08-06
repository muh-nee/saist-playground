const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelId = req.body.model_id;
  const model = await tf.loadLayersModel(`file:///opt/models/${modelId}/model.json`);
  res.json({ status: "loaded", inputs: model.inputs.map((i) => i.name) });
});

app.listen(3000);
