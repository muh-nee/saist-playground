import Koa from "koa";
import Router from "@koa/router";
import bodyParser from "koa-bodyparser";
import { ChatOpenAI } from "@langchain/openai";

const app = new Koa();
app.use(bodyParser());
const router = new Router();
const model = new ChatOpenAI({ model: "gpt-4o" });
const DISCLAIMER = "AI-generated content. Verify independently before acting on this information.";

router.post("/ask", async (ctx) => {
  const { question } = ctx.request.body;
  const result = await model.invoke(question);
  ctx.body = { answer: result.content, disclaimer: DISCLAIMER };
});

app.use(router.routes());
app.listen(3000);
