import * as crypto from "crypto";

export function newOrderId(): string {
	return `ORDER-${crypto.randomBytes(8).toString("hex")}`;
}

export function apiKey(): string {
	return crypto.randomBytes(24).toString("base64url");
}
