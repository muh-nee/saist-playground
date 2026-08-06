import { FaissStore } from "@langchain/community/vectorstores/faiss";
import { OpenAIEmbeddings } from "@langchain/openai";
import { readFileSync } from "fs";

interface DocEntry {
  content: string;
  metadata: Record<string, string>;
}

const embeddings = new OpenAIEmbeddings();
const internalDocs: DocEntry[] = JSON.parse(readFileSync("./internal_docs.json", "utf-8"));
const index = await FaissStore.fromTexts(
  internalDocs.map((d) => d.content),
  internalDocs.map((d) => d.metadata),
  embeddings,
);

async function queryKnowledgeBase(question: string) {
  const retriever = index.asRetriever();
  return retriever.getRelevantDocuments(question);
}
