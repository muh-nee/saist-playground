import express from "express";
import weaviate from "weaviate-client";

const app = express();
app.use(express.json());

const client = await weaviate.connectToLocal();
const collection = client.collections.get("Article");

app.post("/articles", async (req, res) => {
  const { content, author } = req.body;
  await collection.data.insert({ properties: { content, author } });
  res.json({ status: "added" });
});

app.listen(3000);
