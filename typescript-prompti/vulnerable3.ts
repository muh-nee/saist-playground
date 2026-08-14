import { ChatOpenAI } from "@langchain/openai";
import { PromptTemplate } from "@langchain/core/prompts";
import { LLMChain } from "langchain/chains";
import express from "express";

const app = express();
app.use(express.json());

app.post("/summarize", async (req, res) => {
	const email = req.body.email as string;
	const prompt = PromptTemplate.fromTemplate(`Summarize this email: ${email}`);
	const chain = new LLMChain({ llm: new ChatOpenAI(), prompt });
	const result = await chain.invoke({});
	res.json({ summary: result });
});
