const { AutoTokenizer } = require("@huggingface/transformers");
const express = require("express");

const app = express();

app.get("/tokenizer", async (req, res) => {
  const modelName = req.query.model;
  const tokenizer = await AutoTokenizer.from_pretrained(modelName);
  const encoded = tokenizer("hello world");
  res.json({ status: "loaded", tokens: encoded });
});

app.listen(3000);
