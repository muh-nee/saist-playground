import express from "express";
import { QdrantClient } from "@qdrant/js-client-rest";
import OpenAI from "openai";

const app = express();
app.use(express.json());

const client = new QdrantClient({ url: "http://localhost:6333" });
const openai = new OpenAI();

app.post("/store", async (req, res) => {
  const { text } = req.body;
  const embeddingResponse = await openai.embeddings.create({
    model: "text-embedding-3-small",
    input: text,
  });
  const vector = embeddingResponse.data[0].embedding;
  await client.upsert("documents", {
    wait: true,
    points: [{ id: crypto.randomUUID(), vector, payload: { text } }],
  });
  res.json({ status: "stored" });
});

app.listen(3000);
