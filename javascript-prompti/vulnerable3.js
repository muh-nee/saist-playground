const { ChatOpenAI } = require("@langchain/openai");
const { LLMChain, PromptTemplate } = require("langchain");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/summarize", async (req, res) => {
	const email = req.body.email;
	const fullPrompt = "Summarize this email: " + email;
	const template = PromptTemplate.fromTemplate(fullPrompt);
	const chain = new LLMChain({ llm: new ChatOpenAI(), prompt: template });
	const result = await chain.run({});
	res.json({ summary: result });
});
