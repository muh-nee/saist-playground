const { InferenceSession, Tensor } = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

const sessionPromise = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req, res) => {
  const session = await sessionPromise;
  const features = new Float32Array(req.body.features);
  const tensor = new Tensor("float32", features, [1, features.length]);
  const results = await session.run({ input: tensor });
  res.json({ result: Array.from(results.output.data) });
});
