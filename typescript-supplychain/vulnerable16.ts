import { execSync } from "child_process";
import OpenAI from "openai";
import express, { Request, Response } from "express";

const app = express();
app.use(express.json());
const client = new OpenAI();

app.post("/setup-deps", async (req: Request, res: Response) => {
  const feature: string = req.body.feature;
  const completion = await client.chat.completions.create({
    model: "gpt-4",
    messages: [{ role: "user", content: `List npm packages for: ${feature}. One package name per line.` }],
  });
  const packages: string[] = completion.choices[0].message.content!.trim().split("\n");
  for (const pkg of packages) {
    execSync(`npm install ${pkg.trim()}`);
  }
  res.json({ installed: packages });
});
