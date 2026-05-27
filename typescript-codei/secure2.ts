import express, { Request, Response } from "express";

type Op = "square" | "cube";

interface RunBody {
	op: Op;
	value: number | string;
}

const HANDLERS: Record<Op, (x: number) => number> = {
	square: (x) => x * x,
	cube: (x) => x * x * x,
};

const app = express();

app.post("/run", (req: Request<unknown, unknown, RunBody>, res: Response) => {
	const handler = HANDLERS[req.body.op];
	if (!handler) return res.status(400).send("unknown op");
	res.json({ result: handler(Number(req.body.value)) });
});
