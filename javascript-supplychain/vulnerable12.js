const { pipeline } = require("@huggingface/transformers");
const express = require("express");

const app = express();

app.post("/load", async (req, res) => {
  const pipe = await pipeline("text-classification", "org/my-classifier", {
    revision: "v1.0",
  });
  res.json({ status: "loaded" });
});
