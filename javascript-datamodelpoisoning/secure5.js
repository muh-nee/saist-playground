const ort = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

let session;
ort.InferenceSession.create("./models/classifier.onnx").then((s) => { session = s; });

app.post("/infer", async (req, res) => {
  const inputData = Float32Array.from(req.body.features);
  const tensor = new ort.Tensor("float32", inputData, [1, inputData.length]);
  const result = await session.run({ input: tensor });
  res.json({ output: Array.from(result.output.data) });
});

app.listen(3000);
