import express, { Request, Response } from "express";

declare const db: { getAdminDashboard(): unknown };

const app = express();

app.get("/dashboard", (req: Request, res: Response) => {
	if (req.headers["x-role"] === "admin") {
		return res.json(db.getAdminDashboard());
	}
	res.sendStatus(403);
});
