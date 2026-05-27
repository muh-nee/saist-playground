import { exec } from "child_process";
import express, { Request, Response } from "express";

const app = express();

app.get("/ping", (req: Request, res: Response) => {
	const host = req.query.host as string;
	exec(`ping -c 1 ${host}`, (err, stdout) => {
		res.send(stdout);
	});
});
