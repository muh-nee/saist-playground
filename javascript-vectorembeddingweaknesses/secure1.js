import express from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "knowledge_base" });

app.post("/ingest", async (req, res) => {
  if (!req.user?.isAdmin) {
    return res.status(403).json({ error: "Forbidden" });
  }
  const { text } = req.body;
  await vectorStore.addDocuments([{ pageContent: text, metadata: {} }]);
  res.json({ status: "added" });
});

app.listen(3000);
