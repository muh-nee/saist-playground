import express, { Request, Response, RequestHandler } from "express";

interface SessionRequest extends Request {
	session: { userId: string };
}

declare const isLoggedIn: RequestHandler;
declare const db: { findOrdersByUserId(id: string): unknown };

const app = express();

app.get("/orders", isLoggedIn, (req: SessionRequest, res: Response) => {
	const orders = db.findOrdersByUserId(req.session.userId);
	res.json(orders);
});

app.get("/orders/:userId", isLoggedIn, (req: SessionRequest & Request<{ userId: string }>, res: Response) => {
	if (req.params.userId !== req.session.userId) return res.sendStatus(403);
	const orders = db.findOrdersByUserId(req.params.userId);
	res.json(orders);
});
