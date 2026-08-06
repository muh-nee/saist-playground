import express from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "docs" });

app.post("/ingest", async (req, res) => {
  const { text, source } = req.body;
  await vectorStore.addDocuments([{ pageContent: text, metadata: { source } }]);
  res.json({ status: "ok" });
});

app.listen(3000);
