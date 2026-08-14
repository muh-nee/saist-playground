const { OpenAI } = require("openai");
const { MemoryVectorStore } = require("langchain/vectorstores/memory");

const client = new OpenAI();

async function summarizeAndStore(userQuery, sessionId) {
  const response = await client.chat.completions.create({
    model: "gpt-4o",
    messages: [{ role: "user", content: userQuery }],
  });
  const llmOutput = response.choices[0].message.content;
  await vectorStore.addDocuments([{ pageContent: llmOutput, metadata: { sessionId } }]);
  return "stored";
}
