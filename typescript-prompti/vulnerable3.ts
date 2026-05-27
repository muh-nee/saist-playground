import { ChatOpenAI } from "@langchain/openai";
import { PromptTemplate } from "@langchain/core/prompts";
import { LLMChain } from "langchain/chains";

export async function summarize(email: string) {
	const prompt = PromptTemplate.fromTemplate(`Summarize this email: ${email}`);
	const chain = new LLMChain({ llm: new ChatOpenAI(), prompt });
	return chain.invoke({});
}
