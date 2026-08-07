import express, { Request, Response } from "express";
import { ChatOpenAI, OpenAIEmbeddings } from "@langchain/openai";
import { MemoryVectorStore } from "langchain/vectorstores/memory";
import { createRetrievalChain } from "langchain/chains/retrieval";
import { createStuffDocumentsChain } from "langchain/chains/combine_documents";
import { ChatPromptTemplate } from "@langchain/core/prompts";

const app = express();
app.use(express.json());
const embeddings = new OpenAIEmbeddings();
const vectorStore = await MemoryVectorStore.fromTexts(["Policy A", "Policy B"], [{}], embeddings);
const retriever = vectorStore.asRetriever();
const llm = new ChatOpenAI({ model: "gpt-4o" });
const prompt = ChatPromptTemplate.fromTemplate("Context: {context}\nQuestion: {input}");
const combineDocsChain = await createStuffDocumentsChain({ llm, prompt });
const chain = await createRetrievalChain({ retriever, combineDocsChain });

app.post("/query", async (req: Request, res: Response) => {
  const { question } = req.body as { question: string };
  const result = await chain.invoke({ input: question });
  const sources = result.context.map((doc: any) => doc.metadata.source);
  res.json({ answer: result.answer, sources });
});

app.listen(3000);
