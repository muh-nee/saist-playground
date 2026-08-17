const ort = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

const MODEL_PATH = process.env.MODEL_PATH || "./models/classifier.onnx";

function requireAdmin(req, res, next) {
  if (req.headers["x-admin-token"] !== process.env.ADMIN_TOKEN) {
    return res.status(403).json({ error: "forbidden" });
  }
  next();
}

app.post("/admin/load", requireAdmin, async (req, res) => {
  const session = await ort.InferenceSession.create(MODEL_PATH);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
