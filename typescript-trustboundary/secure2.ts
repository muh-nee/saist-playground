import express, { Request, Response } from "express";

interface User {
	id: string;
	role: string;
}

interface SessionRequest extends Request<{ userId: string }> {
	session: { userId: string; lastPromotionActor?: string };
}

declare const db: {
	findUser(id: string): Promise<User>;
	saveUser(user: User): Promise<void>;
};

const app = express();

app.post("/promote/:userId", async (req: SessionRequest, res: Response) => {
	const actor = await db.findUser(req.session.userId);
	if (actor.role !== "admin") return res.sendStatus(403);
	const target = await db.findUser(req.params.userId);
	req.session.lastPromotionActor = actor.id;
	target.role = "admin";
	await db.saveUser(target);
	res.sendStatus(204);
});
