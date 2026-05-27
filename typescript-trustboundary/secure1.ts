import express, { Request, Response } from "express";

interface ProfileBody {
	displayName?: string;
	bio?: string;
}

interface SessionRequest extends Request<unknown, unknown, ProfileBody> {
	session: { user?: { displayName: string; bio: string } };
}

const app = express();

app.post("/profile", (req: SessionRequest, res: Response) => {
	req.session.user = {
		displayName: String(req.body.displayName ?? "").slice(0, 80),
		bio: String(req.body.bio ?? "").slice(0, 500),
	};
	res.sendStatus(200);
});
