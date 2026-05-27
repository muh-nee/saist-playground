import * as xpath from "xpath";
import express, { Request, Response } from "express";

interface LoginBody {
	username: string;
	password: string;
}

declare const doc: Node;
const app = express();

app.post("/login", (req: Request<unknown, unknown, LoginBody>, res: Response) => {
	const query = `//user[name/text()='${req.body.username}' and password/text()='${req.body.password}']`;
	const node = xpath.select(query, doc);
	res.json({ authenticated: !!node });
});
