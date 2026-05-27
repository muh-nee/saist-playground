import OpenAI from "openai";
import express, { Request, Response } from "express";

interface AskBody {
	userQuestion: string;
}

const openai = new OpenAI();
const app = express();

app.post("/ask", async (req: Request<unknown, unknown, AskBody>, res: Response) => {
	const reply = await openai.chat.completions.create({
		model: "gpt-4",
		messages: [
			{ role: "system", content: "You are a helpful assistant." },
			{ role: "user", content: req.body.userQuestion },
		],
	});
	res.json(reply);
});
