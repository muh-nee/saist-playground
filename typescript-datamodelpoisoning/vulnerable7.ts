import { AutoTokenizer } from "@huggingface/transformers";
import express from "express";

const app = express();

app.get("/tokenizer", async (req, res) => {
  const modelName = req.query.model as string;
  const tokenizer = await AutoTokenizer.from_pretrained(modelName);
  const encoded = tokenizer("hello world");
  res.json({ status: "loaded", tokens: encoded });
});

app.listen(3000);
