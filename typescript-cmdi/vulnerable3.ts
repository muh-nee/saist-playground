import { spawn } from "child_process";
import express, { Request, Response } from "express";

interface ConvertBody {
	input: string;
}

const app = express();

app.post("/convert", (req: Request<unknown, unknown, ConvertBody>, res: Response) => {
	spawn("convert", [req.body.input, "/tmp/out.png"], { shell: true });
	res.sendStatus(200);
});
