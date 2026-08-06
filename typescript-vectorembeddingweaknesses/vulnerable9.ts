import express, { Request, Response } from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
let vectorStore: Chroma | null = null;

app.post("/build", async (req: Request, res: Response) => {
  const { texts }: { texts: string[] } = req.body;
  const docs = texts.map((t) => ({ pageContent: t, metadata: {} }));
  vectorStore = await Chroma.fromDocuments(docs, embeddings, { collectionName: "shared_kb" });
  res.json({ status: "built" });
});

app.listen(3000);
