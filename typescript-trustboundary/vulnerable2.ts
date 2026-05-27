import express, { Request, Response } from "express";

interface SessionRequest extends Request {
	session: Record<string, unknown>;
}

const app = express();

app.post("/settings", (req: SessionRequest, res: Response) => {
	Object.assign(req.session, req.body);
	res.sendStatus(200);
});
