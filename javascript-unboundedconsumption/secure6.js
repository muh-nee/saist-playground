const { ChatAnthropic } = require("@langchain/anthropic");
const { HumanMessage } = require("@langchain/core/messages");

const model = new ChatAnthropic({
    model: "claude-3-5-sonnet-20241022",
    maxTokens: 1024,
});

async function analyze(text) {
    const response = await model.invoke([new HumanMessage(text)]);
    return response.content;
}

module.exports = { analyze };
