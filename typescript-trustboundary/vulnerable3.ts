import express, { Request, Response } from "express";

interface PromoteBody {
	isAdmin: boolean;
}

interface SessionRequest extends Request<unknown, unknown, PromoteBody> {
	session: { isAdmin?: boolean };
}

const app = express();

app.post("/promote", (req: SessionRequest, res: Response) => {
	req.session.isAdmin = req.body.isAdmin;
	res.sendStatus(200);
});
