const { ChatOpenAI } = require("@langchain/openai");
const { PromptTemplate, LLMChain } = require("langchain");

async function summarize(email) {
	const prompt = PromptTemplate.fromTemplate(`Summarize this email: ${email}`);
	const chain = new LLMChain({ llm: new ChatOpenAI(), prompt });
	return chain.run({});
}
