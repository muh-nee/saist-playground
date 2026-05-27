import express, { Request, Response } from "express";

declare const db: { findOrdersByUserId(id: string): unknown };

const app = express();

app.get("/orders/:userId", (req: Request<{ userId: string }>, res: Response) => {
	const orders = db.findOrdersByUserId(req.params.userId);
	res.json(orders);
});
