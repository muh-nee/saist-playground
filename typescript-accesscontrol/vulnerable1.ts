import express, { Request, Response } from "express";

declare const db: { getAllUsers(): unknown };

const app = express();

app.get("/admin/users", (req: Request, res: Response) => {
	res.json(db.getAllUsers());
});
