import OpenAI from "openai";
import express from "express";
import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "langchain/embeddings/openai";

const app = express();
const client = new OpenAI();
const vectorStore = new MemoryVectorStore(new OpenAIEmbeddings());

app.use(express.json());

app.post("/summarize", async (req, res) => {
  const { query, sessionId }: { query: string; sessionId: string } = req.body;

  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: query }],
  });
  const llmOutput = response.choices[0].message.content as string;

  await vectorStore.addDocuments([{ pageContent: llmOutput, metadata: { sessionId } }]);
  res.json({ stored: true });
});
