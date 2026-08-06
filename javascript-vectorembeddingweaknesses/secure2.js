import express from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const ALLOWED_CATEGORIES = new Set(["electronics", "clothing", "furniture", "books", "sports"]);
const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "catalog" });

app.post("/ingest", async (req, res) => {
  const { category } = req.body;
  if (!ALLOWED_CATEGORIES.has(category)) {
    return res.status(400).json({ error: "Invalid category" });
  }
  await vectorStore.addDocuments([{ pageContent: category, metadata: {} }]);
  res.json({ status: "ok" });
});

app.listen(3000);
