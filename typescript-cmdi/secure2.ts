import { exec } from "child_process";
import express, { Request, Response } from "express";

const app = express();

const allowedHosts: ReadonlySet<string> = new Set(["host1", "host2", "host3"]);

app.get("/ping", (req: Request, res: Response) => {
	const host = req.query.host as string;
	if (!allowedHosts.has(host)) {
		return res.status(400).send("invalid host");
	}
	exec(`ping -c 1 ${host}`, (err, stdout) => res.send(stdout));
});
