import { pipeline } from "@huggingface/transformers";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const pipe = await pipeline("text-classification", "org/my-classifier", {
    revision: "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2",
  });
  res.json({ status: "loaded" });
});
