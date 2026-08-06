import { AutoModel } from "@huggingface/transformers";
import express from "express";

const app = express();
app.use(express.json());

const PINNED_REVISION = "a3d8e194af7a0c32a0c4f1a62f5cc15d3fe05095";

app.post("/load-model", async (req, res) => {
  const modelName = req.body.model as string;
  const model = await AutoModel.from_pretrained(modelName, { revision: PINNED_REVISION });
  res.json({ status: "loaded" });
});

app.listen(3000);
