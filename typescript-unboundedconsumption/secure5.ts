import { ChatOpenAI } from "@langchain/openai";
import { HumanMessage } from "@langchain/core/messages";

const model = new ChatOpenAI({
    model: "gpt-4o",
    maxTokens: 800,
});

async function askQuestion(question: string): Promise<string> {
    const response = await model.invoke([new HumanMessage(question)]);
    return response.content as string;
}

export { askQuestion };
