const { ChatOpenAI } = require("@langchain/openai");
const { PromptTemplate, LLMChain } = require("langchain");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/summarize", async (req, res) => {
	const email = req.body.email;
	const prompt = PromptTemplate.fromTemplate(`Summarize this email: ${email}`);
	const chain = new LLMChain({ llm: new ChatOpenAI(), prompt });
	const result = await chain.run({});
	res.json({ summary: result });
});
