import express, { Request, Response } from "express";

interface CalcBody {
	expression: string;
}

const app = express();

app.post("/calculate", (req: Request<unknown, unknown, CalcBody>, res: Response) => {
	const value = parseFloat(req.body.expression);
	if (!Number.isFinite(value)) return res.status(400).send("invalid");
	res.json({ result: value * 2 });
});
