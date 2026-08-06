import express from "express";
import { Pinecone } from "@pinecone-database/pinecone";
import OpenAI from "openai";

const app = express();
app.use(express.json());

const pc = new Pinecone();
const index = pc.index("knowledge-base");
const openai = new OpenAI();

app.post("/upsert", async (req, res) => {
  const { text } = req.body;
  const embeddingResponse = await openai.embeddings.create({
    model: "text-embedding-3-small",
    input: text,
  });
  const vector = embeddingResponse.data[0].embedding;
  await index.upsert([{ id: crypto.randomUUID(), values: vector, metadata: { text } }]);
  res.json({ status: "ok" });
});

app.listen(3000);
