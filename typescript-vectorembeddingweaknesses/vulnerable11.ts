import express, { Request, Response } from "express";
import weaviate from "weaviate-ts-client";
import OpenAI from "openai";

const app = express();
app.use(express.json());

const client = weaviate.client({ scheme: "http", host: "localhost:8080" });
const openai = new OpenAI();

app.post("/articles", async (req: Request, res: Response) => {
  const content: string = req.body.content;
  const title: string = req.body.title;
  const embeddingResponse = await openai.embeddings.create({
    model: "text-embedding-3-small",
    input: content,
  });
  await client.data
    .creator()
    .withClassName("Article")
    .withProperties({ content, title })
    .do();
  res.json({ status: "added" });
});

app.listen(3000);
