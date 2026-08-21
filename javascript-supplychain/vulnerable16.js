const { execSync } = require("child_process");
const OpenAI = require("openai");
const express = require("express");

const app = express();
app.use(express.json());
const client = new OpenAI();

app.post("/setup-deps", async (req, res) => {
  const { feature } = req.body;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `List npm packages for: ${feature}. One package name per line.` }],
  });
  const packages = completion.choices[0].message.content.trim().split("\n");
  for (const pkg of packages) {
    execSync(`npm install ${pkg.trim()}`);
  }
  res.json({ installed: packages });
});
