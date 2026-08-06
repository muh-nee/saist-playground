import { Hono } from "hono";
import { QdrantVectorStore } from "@langchain/qdrant";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = new Hono();

const embeddings = new OpenAIEmbeddings();
const vectorStore = await QdrantVectorStore.fromExistingCollection(embeddings, {
  url: "http://localhost:6333",
  collectionName: "docs",
});

app.post("/ingest", async (c) => {
  const { text } = await c.req.json();
  await vectorStore.addDocuments([{ pageContent: text, metadata: {} }]);
  return c.json({ status: "ok" });
});

export default app;
