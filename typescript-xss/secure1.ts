import express, { Request, Response } from "express";
import escapeHtml from "escape-html";

const app = express();

app.get("/search", (req: Request, res: Response) => {
	const q = req.query.q as string;
	res.send(`<h1>Results for ${escapeHtml(q)}</h1>`);
});

interface User {
	bio: string;
}

export function renderProfile(user: User): void {
	const el = document.getElementById("bio") as HTMLElement;
	el.textContent = user.bio;
}
