import Koa from "koa";
import Router from "@koa/router";
import bodyParser from "koa-bodyparser";
import { ChatOpenAI } from "@langchain/openai";
import { StringOutputParser } from "@langchain/core/output_parsers";
import { PromptTemplate } from "@langchain/core/prompts";

const app = new Koa();
app.use(bodyParser());
const router = new Router();
const model = new ChatOpenAI({ model: "gpt-4o" });
const chain = PromptTemplate.fromTemplate("Answer: {question}").pipe(model).pipe(new StringOutputParser());

router.post("/ask", async (ctx) => {
  const { question } = ctx.request.body;
  const answer = await chain.invoke({ question });
  ctx.body = { answer };
});

app.use(router.routes());
app.listen(3000);
