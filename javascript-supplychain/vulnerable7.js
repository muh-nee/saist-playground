const { pipeline } = require("@huggingface/transformers");
const Fastify = require("fastify");

const app = Fastify();

app.post("/load", async (request, reply) => {
  const pipe = await pipeline("text-classification", "org/my-classifier", {
    revision: "main",
  });
  return { status: "loaded" };
});
