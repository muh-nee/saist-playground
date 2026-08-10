const { ChatOpenAI } = require("@langchain/openai");
const { PromptTemplate } = require("@langchain/core/prompts");
const { StringOutputParser } = require("@langchain/core/output_parsers");

const model = new ChatOpenAI({
    model: "gpt-4o",
});

const template = PromptTemplate.fromTemplate(
    "Translate the following text to French: {text}"
);

const chain = template.pipe(model).pipe(new StringOutputParser());

async function translate(text) {
    return chain.invoke({ text });
}

module.exports = { translate };
