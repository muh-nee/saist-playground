import * as xpath from "xpath";
import express, { Request, Response } from "express";

interface LoginBody {
	username: string;
}

declare const doc: Node;
const app = express();

const userQuery = xpath.parse("//user[name/text() = $username]");

app.post("/login", (req: Request<unknown, unknown, LoginBody>, res: Response) => {
	const result = userQuery.select({
		node: doc,
		variables: { username: req.body.username },
	});
	res.json({ authenticated: result.length > 0 });
});
