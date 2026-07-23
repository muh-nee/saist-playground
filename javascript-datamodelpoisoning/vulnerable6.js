const { pipeline } = require("@huggingface/transformers");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/classify", async (req, res) => {
  const modelName = req.body.model;
  const pipe = await pipeline("text-classification", modelName);
  const result = await pipe(req.body.text);
  res.json(result);
});

app.listen(3000);
