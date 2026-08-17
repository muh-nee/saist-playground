import { AutoTokenizer } from "@huggingface/transformers";
import express from "express";

const app = express();
app.use(express.json());

const TOKENIZER_NAME = "Xenova/distilbert-base-uncased-finetuned-sst-2-english";
let tokenizer: Awaited<ReturnType<typeof AutoTokenizer.from_pretrained>>;

async function init(): Promise<void> {
  tokenizer = await AutoTokenizer.from_pretrained(TOKENIZER_NAME);
}

app.post("/tokenize", async (req, res) => {
  const result = await tokenizer(req.body.text as string);
  res.json(result);
});

init().then(() => app.listen(3000));
