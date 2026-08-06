import express from "express";
import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
let vectorStore = await MemoryVectorStore.fromTexts(["init"], [{}], embeddings);

app.post("/build-index", async (req, res) => {
  const { texts } = req.body;
  vectorStore = await MemoryVectorStore.fromTexts(texts, texts.map(() => ({})), embeddings);
  res.json({ status: "index built" });
});

app.listen(3000);
