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
			{ role: "user", content: `You are a helpful assistant. ${req.body.userQuestion}` },
		],
	});
	res.json(reply);
});
