const tf = require("@tensorflow/tfjs-node");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/saved-model", async (req, res) => {
  const modelPath = req.body.path;
  const model = await tf.node.loadSavedModel(modelPath);
  res.json({ status: "loaded" });
});

app.listen(3000);
