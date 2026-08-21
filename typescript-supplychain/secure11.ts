import { exec } from "child_process";
import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());
const client = new OpenAI();
const APPROVED_PACKAGES: ReadonlySet<string> = new Set(["onnxruntime-node", "@tensorflow/tfjs-node", "@huggingface/transformers", "brain.js"]);

app.post("/install-package", async (req: Request, res: Response) => {
  const task: string = req.body.task;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `What npm package for: ${task}? Reply with only the package name.` }],
  });
  const packageName: string = completion.choices[0].message.content!.trim();
  if (!APPROVED_PACKAGES.has(packageName)) {
    return res.status(400).json({ error: "package not approved" });
  }
  exec(`npm install ${packageName}`, (err) => {
    res.json(err ? { error: err.message } : { installed: packageName });
  });
});
