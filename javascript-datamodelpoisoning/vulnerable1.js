const ort = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load", async (req, res) => {
  const modelPath = req.body.model_path;
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
