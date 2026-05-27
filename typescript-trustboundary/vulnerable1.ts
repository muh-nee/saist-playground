import express, { Request, Response } from "express";

interface SessionRequest extends Request {
	session: { user?: unknown };
}

const app = express();

app.post("/profile", (req: SessionRequest, res: Response) => {
	req.session.user = req.body;
	res.sendStatus(200);
});
