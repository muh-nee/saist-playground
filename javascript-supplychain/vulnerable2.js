const { InferenceSession } = require("onnxruntime-node");
const axios = require("axios");
const Fastify = require("fastify");

const app = Fastify();
const MODEL_URL = "https://models.example.com/embedder.onnx";

app.post("/load", async (request, reply) => {
  const response = await axios.get(MODEL_URL, { responseType: "arraybuffer" });
  const session = await InferenceSession.create(response.data);
  return { status: "loaded" };
});
