const ort = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

const TRUSTED_REGISTRY = "https://models.trusted-org.example.com/";

app.post("/load-remote", async (req, res) => {
  const modelUrl = req.body.model_url;
  if (!modelUrl.startsWith(TRUSTED_REGISTRY)) {
    return res.status(403).json({ error: "untrusted model URL" });
  }
  const response = await fetch(modelUrl);
  const buffer = await response.arrayBuffer();
  const session = await ort.InferenceSession.create(Buffer.from(buffer));
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
