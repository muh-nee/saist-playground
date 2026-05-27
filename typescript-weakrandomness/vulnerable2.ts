import express, { Request, Response } from "express";

const app = express();

app.post("/login", (req: Request, res: Response) => {
	const sessionId = Math.floor(Math.random() * 1e16).toString();
	res.cookie("sid", sessionId);
	res.sendStatus(200);
});
