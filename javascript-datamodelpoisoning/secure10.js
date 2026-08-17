const { AutoTokenizer } = require("@huggingface/transformers");
const express = require("express");

const app = express();
app.use(express.json());

const TOKENIZER_NAME = "Xenova/distilbert-base-uncased-finetuned-sst-2-english";
let tokenizer;

async function init() {
  tokenizer = await AutoTokenizer.from_pretrained(TOKENIZER_NAME);
}

app.post("/tokenize", async (req, res) => {
  const result = await tokenizer(req.body.text);
  res.json(result);
});

init().then(() => app.listen(3000));
