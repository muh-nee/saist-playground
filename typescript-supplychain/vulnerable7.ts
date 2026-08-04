import { pipeline } from "@huggingface/transformers";
import Fastify from "fastify";

const app = Fastify();

app.post("/load", async (request, reply) => {
  const pipe = await pipeline("text-classification", "org/my-classifier", {
    revision: "main",
  });
  return { status: "loaded" };
});
