import { pipeline } from "@huggingface/transformers";
import express from "express";

const app = express();
app.use(express.json());

app.post("/classify", async (req, res) => {
  const modelName = req.body.model as string;
  const pipe = await pipeline("text-classification", modelName);
  const result = await pipe(req.body.text);
  res.json(result);
});

app.listen(3000);
