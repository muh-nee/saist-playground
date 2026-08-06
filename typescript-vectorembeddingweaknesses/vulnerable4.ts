import express, { Request, Response } from "express";
import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
let vectorStore: MemoryVectorStore = await MemoryVectorStore.fromTexts(["init"], [{}], embeddings);

app.post("/build-index", async (req: Request, res: Response) => {
  const { texts }: { texts: string[] } = req.body;
  vectorStore = await MemoryVectorStore.fromTexts(texts, texts.map(() => ({})), embeddings);
  res.json({ status: "index built" });
});

app.listen(3000);
