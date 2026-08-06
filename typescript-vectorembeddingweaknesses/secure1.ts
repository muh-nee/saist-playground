import express, { Request, Response } from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "knowledge_base" });

app.post("/ingest", async (req: Request, res: Response) => {
  if (!req.user?.isAdmin) {
    return res.status(403).json({ error: "Forbidden" });
  }
  const text: string = req.body.text;
  await vectorStore.addDocuments([{ pageContent: text, metadata: {} }]);
  res.json({ status: "added" });
});

app.listen(3000);
