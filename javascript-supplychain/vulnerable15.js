const { exec } = require("child_process");
const OpenAI = require("openai");
const express = require("express");

const app = express();
app.use(express.json());
const client = new OpenAI();

app.post("/install-package", async (req, res) => {
  const { task } = req.body;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `What npm package should I use for: ${task}? Reply with only the package name.` }],
  });
  const packageName = completion.choices[0].message.content.trim();
  exec(`npm install ${packageName}`, (err) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json({ installed: packageName });
  });
});
