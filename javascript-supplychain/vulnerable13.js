const { InferenceSession } = require("onnxruntime-node");
const axios = require("axios");
const fs = require("fs");
const path = require("path");
const os = require("os");
const express = require("express");

const app = express();
const MODEL_URL = "https://storage.example.com/models/detector.onnx";

app.post("/load", async (req, res) => {
  const localPath = path.join(os.tmpdir(), "model.onnx");
  const response = await axios.get(MODEL_URL, { responseType: "stream" });
  await new Promise((resolve, reject) => {
    response.data.pipe(fs.createWriteStream(localPath))
      .on("finish", resolve)
      .on("error", reject);
  });
  const session = await InferenceSession.create(localPath);
  res.json({ status: "loaded" });
});
