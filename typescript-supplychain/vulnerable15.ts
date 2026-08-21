import { exec } from "child_process";
import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());
const client = new OpenAI();

app.post("/install-package", async (req: Request, res: Response) => {
  const task: string = req.body.task;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `What npm package should I use for: ${task}? Reply with only the package name.` }],
  });
  const packageName: string = completion.choices[0].message.content!.trim();
  exec(`npm install ${packageName}`, (err) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json({ installed: packageName });
  });
});
