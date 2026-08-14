const { OpenAI } = require("openai");
const express = require("express");
const { MemoryVectorStore } = require("langchain/vectorstores/memory");
const { OpenAIEmbeddings } = require("langchain/embeddings/openai");

const app = express();
const client = new OpenAI();
const vectorStore = new MemoryVectorStore(new OpenAIEmbeddings());

app.use(express.json());

app.post("/summarize", async (req, res) => {
  const { query, sessionId } = req.body;

  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: query }],
  });
  const llmOutput = response.choices[0].message.content;

  await vectorStore.addDocuments([{ pageContent: llmOutput, metadata: { sessionId } }]);
  res.json({ stored: true });
});
