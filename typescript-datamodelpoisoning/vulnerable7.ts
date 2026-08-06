import { AutoTokenizer } from "@huggingface/transformers";
import express from "express";

const app = express();

const PINNED_REVISION = "a3d8e194af7a0c32a0c4f1a62f5cc15d3fe05095";

app.get("/tokenizer", async (req, res) => {
  const modelName = req.query.model as string;
  const tokenizer = await AutoTokenizer.from_pretrained(modelName, { revision: PINNED_REVISION });
  const encoded = tokenizer("hello world");
  res.json({ status: "loaded", tokens: encoded });
});

app.listen(3000);
