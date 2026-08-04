const tf = require("@tensorflow/tfjs-node");
const Fastify = require("fastify");

const app = Fastify();
const MODEL_URL = "https://storage.example.com/models/graph/model.json";

app.post("/load", async (request, reply) => {
  const model = await tf.loadGraphModel(MODEL_URL);
  return { status: "loaded" };
});
