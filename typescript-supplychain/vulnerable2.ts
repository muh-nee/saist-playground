import { InferenceSession } from "onnxruntime-node";
import axios from "axios";
import Fastify from "fastify";

const app = Fastify();
const MODEL_URL = "https://models.example.com/embedder.onnx";

app.post("/load", async (request, reply) => {
  const response = await axios.get<ArrayBuffer>(MODEL_URL, { responseType: "arraybuffer" });
  const session = await InferenceSession.create(Buffer.from(response.data));
  return { status: "loaded" };
});
