import express, { Request, Response, NextFunction, RequestHandler } from "express";

interface SessionRequest extends Request {
	session: { role: string };
}

declare const isLoggedIn: RequestHandler;
declare const db: { getAllUsers(): unknown; deleteUser(id: string): void };

const app = express();

function requireRole(role: string): RequestHandler {
	return (req: SessionRequest, res: Response, next: NextFunction) => {
		if (req.session.role !== role) return res.sendStatus(403);
		next();
	};
}

app.get("/admin/users", isLoggedIn, requireRole("admin"), (req: Request, res: Response) => {
	res.json(db.getAllUsers());
});

app.post("/users/:id/delete", isLoggedIn, requireRole("admin"), (req: Request<{ id: string }>, res: Response) => {
	db.deleteUser(req.params.id);
	res.sendStatus(204);
});
