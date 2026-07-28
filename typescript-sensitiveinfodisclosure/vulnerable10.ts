import { VectorStoreIndex, SimpleDirectoryReader } from "llamaindex";

async function queryWithSecret(): Promise<string> {
  const paymentKey: string = process.env.PAYMENT_API_KEY!;
  const documents = await new SimpleDirectoryReader().loadData("./docs");
  const index = await VectorStoreIndex.fromDocuments(documents);
  const queryEngine = index.asQueryEngine();
  const response = await queryEngine.query({
    query: `Payment authentication failing. Key in use: ${paymentKey}. What could be wrong?`,
  });
  return response.response;
}
