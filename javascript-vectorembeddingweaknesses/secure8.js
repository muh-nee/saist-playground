import { FaissStore } from "@langchain/community/vectorstores/faiss";
import { OpenAIEmbeddings } from "@langchain/openai";
import { readFileSync } from "fs";

const embeddings = new OpenAIEmbeddings();
const internalDocs = JSON.parse(readFileSync("./internal_docs.json", "utf-8"));
const index = await FaissStore.fromTexts(
  internalDocs.map((d) => d.content),
  internalDocs.map((d) => d.metadata),
  embeddings,
);

async function queryKnowledgeBase(question) {
  const retriever = index.asRetriever();
  return retriever.getRelevantDocuments(question);
}
