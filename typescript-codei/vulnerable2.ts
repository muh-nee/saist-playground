import express, { Request, Response } from "express";

interface RunBody {
	args: string;
	body: string;
}

const app = express();

app.post("/run", (req: Request<unknown, unknown, RunBody>, res: Response) => {
	const fn = new Function(req.body.args, req.body.body);
	res.json({ result: fn() });
});
