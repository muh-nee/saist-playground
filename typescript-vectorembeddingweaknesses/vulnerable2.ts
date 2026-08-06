import Fastify from "fastify";
import { ChromaClient, OpenAIEmbeddingFunction } from "chromadb";

const fastify = Fastify();

const chroma = new ChromaClient({ path: "http://localhost:8000" });
const embedder = new OpenAIEmbeddingFunction({ openai_api_key: process.env.OPENAI_API_KEY! });
const collection = await chroma.getOrCreateCollection({ name: "support_kb", embeddingFunction: embedder });

interface UpsertBody {
  id: string;
  content: string;
}

fastify.post<{ Body: UpsertBody }>("/update", async (request, reply) => {
  const { id, content } = request.body;
  await collection.upsert({
    ids: [id],
    documents: [content],
  });
  return { status: "updated" };
});

fastify.listen({ port: 3000 });
