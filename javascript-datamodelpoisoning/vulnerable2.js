const ort = require("onnxruntime-node");
const express = require("express");

const app = express();

app.get("/load", async (req, res) => {
  const modelPath = req.query.model;
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
