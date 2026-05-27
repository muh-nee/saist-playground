import * as fs from "fs";
import express, { Request, Response } from "express";

const app = express();

app.get("/download", (req: Request, res: Response) => {
	const path = req.query.path as string;
	fs.createReadStream(path).pipe(res);
});
