import * as path from "path";
import express, { Request, Response } from "express";

const app = express();

app.get("/static/:file", (req: Request<{ file: string }>, res: Response) => {
	res.sendFile(path.join(__dirname, "uploads", req.params.file));
});
