import express, { Request, Response } from "express";

const app = express();

app.get("/search", (req: Request, res: Response) => {
	const q = req.query.q as string;
	res.send(`<h1>Results for ${q}</h1>`);
});
