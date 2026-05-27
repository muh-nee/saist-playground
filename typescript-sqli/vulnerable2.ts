import type { Request, Response } from "express";

export default function handler(req: Request, res: Response) {
	const id = req.query.id as string;
	connection.query("SELECT * FROM users WHERE id = " + id, (err: any, rows: any) => {
		res.json(rows);
	});
}
