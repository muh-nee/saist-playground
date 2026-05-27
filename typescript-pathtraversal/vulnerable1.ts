import * as fs from "fs";
import express, { Request, Response } from "express";

const app = express();

app.get("/file", (req: Request, res: Response) => {
	const name = req.query.name as string;
	fs.readFile(`/var/data/${name}`, "utf8", (err, data) => {
		res.send(data);
	});
});
