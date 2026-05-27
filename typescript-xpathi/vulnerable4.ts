import * as xpath from "xpath";
import express, { Request, Response } from "express";

declare const ordersDoc: Node;
const app = express();

app.get("/orders", (req: Request, res: Response) => {
	const customer = req.query.customer as string;
	const expr = "//orders/order[customer='" + customer + "']";
	const nodes = xpath.select(expr, ordersDoc);
	res.json(nodes.map((n) => n.toString()));
});
