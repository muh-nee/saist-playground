import express, { Request, Response } from "express";

interface CalcBody {
	expression: string;
}

const app = express();

app.post("/calculate", (req: Request<unknown, unknown, CalcBody>, res: Response) => {
	const result = eval(req.body.expression);
	res.json({ result });
});
