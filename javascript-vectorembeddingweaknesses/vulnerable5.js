import { Hono } from "hono";
import { FaissStore } from "@langchain/community/vectorstores/faiss";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = new Hono();
const embeddings = new OpenAIEmbeddings();
const vectorStore = await FaissStore.fromTexts(["init"], [{}], embeddings);

app.post("/articles", async (c) => {
  const { content, author } = await c.req.json();
  await vectorStore.addDocuments([{ pageContent: content, metadata: { author } }]);
  return c.json({ status: "added" });
});

export default app;
