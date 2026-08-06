import express from "express";
import { Chroma } from "@langchain/community/vectorstores/chroma";
import { OpenAIEmbeddings } from "@langchain/openai";

const app = express();
app.use(express.json());

const embeddings = new OpenAIEmbeddings();
let vectorStore = null;

app.post("/build", async (req, res) => {
  const { texts } = req.body;
  const docs = texts.map((t) => ({ pageContent: t, metadata: {} }));
  vectorStore = await Chroma.fromDocuments(docs, embeddings, { collectionName: "shared_kb" });
  res.json({ status: "built" });
});

app.listen(3000);
