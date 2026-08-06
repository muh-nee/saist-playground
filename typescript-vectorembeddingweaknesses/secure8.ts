import express, { Request, Response } from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
const vectorStore = new Chroma(embeddings, { collectionName: "docs" });

app.post("/search", async (req: Request, res: Response) => {
  const { query }: { query: string } = req.body;
  const results = await vectorStore.similaritySearch(query, 5);
  res.json({ results: results.map((r) => r.pageContent) });
});

app.listen(3000);
