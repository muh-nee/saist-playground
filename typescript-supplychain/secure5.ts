import { AutoModel, AutoTokenizer } from "@huggingface/transformers";
import Fastify from "fastify";

const app = Fastify();

app.post("/load", async (request, reply) => {
  const model = await AutoModel.from_pretrained("org/my-model", {
    revision: "11c5a3d5811f50298f278a704980280950aedb10",
  });
  return { status: "loaded" };
});
