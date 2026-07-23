const ort = require("onnxruntime-node");
const express = require("express");

const app = express();
app.use(express.json());

function requireAdmin(req, res, next) {
  if (req.headers["x-admin-token"] !== process.env.ADMIN_TOKEN) {
    return res.status(403).json({ error: "forbidden" });
  }
  next();
}

app.post("/admin/load", requireAdmin, async (req, res) => {
  const modelPath = req.body.model_path;
  const session = await ort.InferenceSession.create(modelPath);
  res.json({ status: "loaded", inputNames: session.inputNames });
});

app.listen(3000);
