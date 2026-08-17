const { AutoTokenizer } = require("@huggingface/transformers");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load-tokenizer", async (req, res) => {
  const tokenizerName = req.body.tokenizer;
  const tokenizer = await AutoTokenizer.from_pretrained(tokenizerName);
  res.json({ vocabSize: tokenizer.vocab_size });
});

app.listen(3000);
