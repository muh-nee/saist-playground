import express, { Request, Response } from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "docs" });

app.post("/ingest", async (req: Request, res: Response) => {
  const text: string = req.body.text;
  const source: string = req.body.source;
  await vectorStore.addDocuments([{ pageContent: text, metadata: { source } }]);
  res.json({ status: "ok" });
});

app.listen(3000);
