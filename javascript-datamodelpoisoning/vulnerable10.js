const ort = require("onnxruntime-node");
const axios = require("axios");
const express = require("express");

const app = express();
app.use(express.json());

app.post("/load-remote", async (req, res) => {
  const modelUrl = req.body.model_url;
  const response = await axios.get(modelUrl, { responseType: "arraybuffer" });
  const session = await ort.InferenceSession.create(Buffer.from(response.data));
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
