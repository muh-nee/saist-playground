import express, { Request, Response, NextFunction, RequestHandler } from "express";

declare const isLoggedIn: RequestHandler;
declare const db: { deleteUser(id: string): void };

const app = express();

app.post("/users/:id/delete", isLoggedIn, (req: Request<{ id: string }>, res: Response) => {
	db.deleteUser(req.params.id);
	res.sendStatus(204);
});
