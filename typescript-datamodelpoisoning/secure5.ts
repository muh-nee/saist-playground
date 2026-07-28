import * as ort from "onnxruntime-node";
import express from "express";

const app = express();
app.use(express.json());

const session = ort.InferenceSession.create("./models/classifier.onnx");

app.post("/infer", async (req, res) => {
  const s = await session;
  const inputData = Float32Array.from(req.body.features);
  const tensor = new ort.Tensor("float32", inputData, [1, inputData.length]);
  const result = await s.run({ input: tensor });
  res.json({ output: Array.from(result.output.data as Float32Array) });
});

app.listen(3000);
