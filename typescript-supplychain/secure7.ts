import { InferenceSession, Tensor } from "onnxruntime-node";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());

const sessionPromise: Promise<InferenceSession> = InferenceSession.create("./models/classifier.onnx");

app.post("/predict", async (req: Request, res: Response) => {
  const session = await sessionPromise;
  const features = new Float32Array(req.body.features as number[]);
  const tensor = new Tensor("float32", features, [1, features.length]);
  const results = await session.run({ input: tensor });
  res.json({ result: Array.from(results.output.data as Float32Array) });
});
