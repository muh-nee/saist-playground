import express from "express";
import { generateObject } from "ai";
import { openai } from "@ai-sdk/openai";
import { z } from "zod";

const app = express();
app.use(express.json());
const schema = z.object({ summary: z.string(), recommendation: z.string() });

app.post("/analyze", async (req, res) => {
  const { topic } = req.body;
  const { object } = await generateObject({ model: openai("gpt-4o"), schema, prompt: topic });
  res.json(object);
});

app.listen(3000);
