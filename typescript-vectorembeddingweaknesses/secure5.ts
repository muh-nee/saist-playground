import express, { Request, Response } from "express";
import { ChromaClient, OpenAIEmbeddingFunction } from "chromadb";

const app = express();
app.use(express.json());

const chroma = new ChromaClient({ path: "http://localhost:8000" });
const embedder = new OpenAIEmbeddingFunction({ openai_api_key: process.env.OPENAI_API_KEY! });

app.post("/ingest", async (req: Request, res: Response) => {
  const userId: string | undefined = (req.user as any)?.id;
  if (!userId) {
    return res.status(401).json({ error: "Unauthorized" });
  }
  const collectionName = `user_${userId}`;
  const collection = await chroma.getOrCreateCollection({ name: collectionName, embeddingFunction: embedder });
  const { id, content }: { id: string; content: string } = req.body;
  await collection.add({ ids: [id], documents: [content] });
  res.json({ status: "added" });
});

app.listen(3000);
