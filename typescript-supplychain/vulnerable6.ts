import { pipeline } from "@xenova/transformers";
import express from "express";

const app = express();

app.post("/load", async (req, res) => {
  const pipe = await pipeline("text-classification", "org/my-classifier");
  res.json({ status: "loaded" });
});
