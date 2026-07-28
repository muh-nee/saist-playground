import * as ort from "onnxruntime-node";
import axios from "axios";
import express from "express";

const app = express();
app.use(express.json());

app.post("/load-remote", async (req, res) => {
  const modelUrl = req.body.model_url as string;
  const response = await axios.get(modelUrl, { responseType: "arraybuffer" });
  const session = await ort.InferenceSession.create(Buffer.from(response.data));
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
