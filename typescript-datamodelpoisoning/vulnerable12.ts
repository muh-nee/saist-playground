import { AutoModel } from "@huggingface/transformers";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load-model", async (req, res) => {
  const modelName = req.body.model as string;
  const model = await AutoModel.from_pretrained(modelName);
  res.json({ status: "loaded" });
});

app.listen(3000);
