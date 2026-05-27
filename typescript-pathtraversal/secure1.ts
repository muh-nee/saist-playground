import * as fs from "fs";
import * as path from "path";
import express, { Request, Response } from "express";

const app = express();

const BASE = path.resolve("/var/data");

app.get("/file", (req: Request, res: Response) => {
	const name = req.query.name as string;
	const requested = path.resolve(BASE, name);
	if (!requested.startsWith(BASE + path.sep)) {
		return res.status(400).send("invalid path");
	}
	fs.readFile(requested, "utf8", (err, data) => res.send(data));
});
