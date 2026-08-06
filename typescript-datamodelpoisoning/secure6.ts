import * as ort from "onnxruntime-node";
import crypto from "crypto";
import express from "express";

const app = express();
app.use(express.json());

const TRUSTED_REGISTRY = "https://models.trusted-org.example.com/";
const EXPECTED_HASH = "b94f6f125c79e3a5ffaa826f584c10d52ada669e6762051b826b55776d05a3c7";

app.post("/load-remote", async (req, res) => {
  const modelUrl = req.body.model_url as string;
  if (!modelUrl.startsWith(TRUSTED_REGISTRY)) {
    return res.status(403).json({ error: "untrusted model URL" });
  }
  const response = await fetch(modelUrl);
  const buffer = Buffer.from(await response.arrayBuffer());
  const actualHash = crypto.createHash("sha256").update(buffer).digest("hex");
  if (actualHash !== EXPECTED_HASH) {
    return res.status(400).json({ error: "integrity check failed" });
  }
  const session = await ort.InferenceSession.create(buffer);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
