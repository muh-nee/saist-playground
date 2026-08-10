const { ChatOpenAI } = require("@langchain/openai");
const { HumanMessage } = require("@langchain/core/messages");

const model = new ChatOpenAI({
    model: "gpt-4o",
    maxTokens: 800,
});

async function askQuestion(question) {
    const response = await model.invoke([new HumanMessage(question)]);
    return response.content;
}

module.exports = { askQuestion };
