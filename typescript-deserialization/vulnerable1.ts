import * as serialize from "node-serialize";
import express, { Request, Response } from "express";

interface RestoreBody {
	data: string;
}

const app = express();

app.post("/restore", (req: Request<unknown, unknown, RestoreBody>, res: Response) => {
	const obj = serialize.unserialize(req.body.data);
	res.json(obj);
});
