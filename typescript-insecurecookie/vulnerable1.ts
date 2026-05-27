import express, { Request, Response } from "express";

interface AuthedRequest extends Request {
	user: { id: string };
}

const app = express();

app.post("/login", (req: AuthedRequest, res: Response) => {
	res.cookie("sessionId", req.user.id);
	res.sendStatus(200);
});
