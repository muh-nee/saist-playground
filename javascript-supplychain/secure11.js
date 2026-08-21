const { exec } = require("child_process");
const OpenAI = require("openai");
const express = require("express");

const app = express();
app.use(express.json());
const client = new OpenAI();
const APPROVED_PACKAGES = new Set(["onnxruntime-node", "@tensorflow/tfjs-node", "@huggingface/transformers", "brain.js"]);

app.post("/install-package", async (req, res) => {
  const { task } = req.body;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `What npm package for: ${task}? Reply with only the package name.` }],
  });
  const packageName = completion.choices[0].message.content.trim();
  if (!APPROVED_PACKAGES.has(packageName)) {
    return res.status(400).json({ error: "package not approved" });
  }
  exec(`npm install ${packageName}`, (err) => {
    res.json(err ? { error: err.message } : { installed: packageName });
  });
});
