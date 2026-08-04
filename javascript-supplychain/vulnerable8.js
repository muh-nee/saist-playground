const { AutoModel, AutoTokenizer } = require("@huggingface/transformers");
const express = require("express");

const app = express();

app.post("/load", async (req, res) => {
  const model = await AutoModel.from_pretrained("org/my-model");
  const tokenizer = await AutoTokenizer.from_pretrained("org/my-model");
  res.json({ status: "loaded" });
});
