import { pipeline } from "@huggingface/transformers";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const pipe = await pipeline("text-classification", "org/my-classifier", {
    revision: "v1.0",
  });
  res.json({ status: "loaded" });
});
