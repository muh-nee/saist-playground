import express, { Request, Response } from "express";
import weaviate from "weaviate-client";

const app = express();
app.use(express.json());

const client = await weaviate.connectToLocal();
const collection = client.collections.get("Article");

app.post("/articles", async (req: Request, res: Response) => {
  const content: string = req.body.content;
  const author: string = req.body.author;
  await collection.data.insert({ properties: { content, author } });
  res.json({ status: "added" });
});

app.listen(3000);
