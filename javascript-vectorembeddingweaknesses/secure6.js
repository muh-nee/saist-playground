import express from "express";
import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "@langchain/openai";
import OpenAI from "openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const openai = new OpenAI();

app.post("/summarize", async (req, res) => {
  const { text, query } = req.body;
  const vs = await MemoryVectorStore.fromTexts([text], [{}], embeddings);
  const docs = await vs.similaritySearch(query, 3);
  const completion = await openai.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: JSON.stringify(docs) }],
  });
  res.json({ answer: completion.choices[0].message.content });
});

app.listen(3000);
