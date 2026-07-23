const { AutoModel } = require("@huggingface/transformers");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load-model", async (req, res) => {
  const modelName = req.body.model;
  const model = await AutoModel.from_pretrained(modelName);
  res.json({ status: "loaded" });
});

app.listen(3000);
