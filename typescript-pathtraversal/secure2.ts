import * as fs from "fs";
import express, { Request, Response } from "express";

const app = express();

const ALLOWED_FILES: ReadonlySet<string> = new Set(["report.pdf", "summary.csv", "data.json"]);

app.get("/file", (req: Request, res: Response) => {
	const name = req.query.name as string;
	if (!ALLOWED_FILES.has(name)) {
		return res.status(400).send("invalid file");
	}
	fs.readFile(`/var/data/${name}`, "utf8", (err, data) => res.send(data));
});
