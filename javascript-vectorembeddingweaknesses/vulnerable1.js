import express from "express";
import { ChromaClient } from "chromadb";
import { OpenAIEmbeddingFunction } from "chromadb";

const app = express();
app.use(express.json());

const chroma = new ChromaClient({ path: "http://localhost:8000" });
const embedder = new OpenAIEmbeddingFunction({ openai_api_key: process.env.OPENAI_API_KEY });
const collection = await chroma.getOrCreateCollection({ name: "knowledge_base", embeddingFunction: embedder });

app.post("/ingest", async (req, res) => {
  const { id, content } = req.body;
  await collection.add({
    ids: [id],
    documents: [content],
  });
  res.json({ status: "added" });
});

app.listen(3000);
