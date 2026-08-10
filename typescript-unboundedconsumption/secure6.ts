import { ChatAnthropic } from "@langchain/anthropic";
import { HumanMessage } from "@langchain/core/messages";

const model = new ChatAnthropic({
    model: "claude-3-5-sonnet-20241022",
    maxTokens: 1024,
});

async function analyze(text: string): Promise<string> {
    const response = await model.invoke([new HumanMessage(text)]);
    return response.content as string;
}

export { analyze };
