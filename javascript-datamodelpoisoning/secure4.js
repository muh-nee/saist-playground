const { pipeline } = require("@huggingface/transformers");
const express = require("express");

const app = express();
app.use(express.json());

const MODEL_NAME = "Xenova/distilbert-base-uncased-finetuned-sst-2-english";
const MODEL_REVISION = "a3d8e194af7a0c32a0c4f1a62f5cc15d3fe05095";

app.post("/classify", async (req, res) => {
  const pipe = await pipeline("text-classification", MODEL_NAME, { revision: MODEL_REVISION });
  const result = await pipe(req.body.text);
  res.json(result);
});

app.listen(3000);
