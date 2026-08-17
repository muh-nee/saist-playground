import * as brain from "brain.js";
import * as crypto from "crypto";
import express from "express";

const app = express();
app.use(express.json());

const net = new brain.NeuralNetwork();
const EXPECTED_HASH = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
const TRUSTED_DATASET_URL = "https://internal.example.com/datasets/approved.json";

app.post("/train-verified", async (req, res) => {
  const response = await fetch(TRUSTED_DATASET_URL);
  const buffer = Buffer.from(await response.arrayBuffer());
  const hash = crypto.createHash("sha256").update(buffer).digest("hex");
  if (hash !== EXPECTED_HASH) {
    return res.status(403).json({ error: "integrity check failed" });
  }
  const trainingData = JSON.parse(buffer.toString());
  await net.trainAsync(trainingData);
  res.json({ status: "trained" });
});

app.listen(3000);
