import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { OpenAIEmbeddings } from "@langchain/openai";

const embeddings = new OpenAIEmbeddings();

const PRODUCT_DOCS = [
  "Our return policy allows returns within 30 days.",
  "Standard shipping takes 5-7 business days.",
  "Customer support is available Monday through Friday.",
];

const vectorStore = await MemoryVectorStore.fromTexts(
  PRODUCT_DOCS,
  PRODUCT_DOCS.map(() => ({})),
  embeddings,
);
