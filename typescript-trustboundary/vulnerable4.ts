import { Request } from "express";

interface AppRequest extends Request {
	session: { userId: string };
	context?: Record<string, unknown>;
}

export function attachUserContext(req: AppRequest): void {
	req.context = {
		serverTime: Date.now(),
		...req.body,
		userId: req.session.userId,
	};
}
