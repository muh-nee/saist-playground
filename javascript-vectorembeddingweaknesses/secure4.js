import express from "express";
import { ChromaClient } from "chromadb";
import { OpenAIEmbeddingFunction } from "chromadb";

const app = express();
app.use(express.json());

const chroma = new ChromaClient({ path: "http://localhost:8000" });
const embedder = new OpenAIEmbeddingFunction({ openai_api_key: process.env.OPENAI_API_KEY });

app.post("/ingest/:tenantId", async (req, res) => {
  if (req.user?.role !== "admin") {
    return res.status(403).json({ error: "Forbidden" });
  }
  const { tenantId } = req.params;
  const collectionName = `tenant_${tenantId}`;
  const collection = await chroma.getOrCreateCollection({ name: collectionName, embeddingFunction: embedder });
  const { id, content } = req.body;
  await collection.add({ ids: [id], documents: [content] });
  res.json({ status: "added" });
});

app.listen(3000);
