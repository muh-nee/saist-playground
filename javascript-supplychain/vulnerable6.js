const { pipeline } = require("@xenova/transformers");
const express = require("express");

const app = express();

app.post("/load", async (req, res) => {
  const pipe = await pipeline("text-classification", "org/my-classifier");
  res.json({ status: "loaded" });
});
