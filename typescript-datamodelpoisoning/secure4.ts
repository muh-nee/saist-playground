import { pipeline } from "@huggingface/transformers";
import express from "express";

const app = express();
app.use(express.json());

const MODEL_NAME = "Xenova/distilbert-base-uncased-finetuned-sst-2-english";

app.post("/classify", async (req, res) => {
  const pipe = await pipeline("text-classification", MODEL_NAME);
  const result = await pipe(req.body.text);
  res.json(result);
});

app.listen(3000);
