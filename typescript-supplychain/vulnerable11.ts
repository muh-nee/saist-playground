import { InferenceSession } from "onnxruntime-node";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());

app.post("/load", async (req: Request, res: Response) => {
  const modelUrl: string = req.body.modelUrl;
  const resp = await fetch(modelUrl);
  const rawBytes: ArrayBuffer = await resp.arrayBuffer();
  const modelBuffer = Buffer.from(rawBytes);
  const session = await InferenceSession.create(modelBuffer);
  res.json({ status: "loaded" });
});
