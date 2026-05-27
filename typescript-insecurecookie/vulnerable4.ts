import { Response } from "express";

export function setAuthCookie(res: Response, token: string): void {
	res.cookie("auth", token, { httpOnly: true, secure: true });
}
