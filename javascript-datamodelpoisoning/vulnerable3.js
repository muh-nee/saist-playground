const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelUrl = req.body.model_url;
  const model = await tf.loadLayersModel(modelUrl);
  res.json({ status: "loaded", inputs: model.inputs.map((i) => i.name) });
});

app.listen(3000);
