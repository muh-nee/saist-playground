import * as crypto from "crypto";

export function signToken(payload: string, secret: string): string {
	return crypto.createHmac("md5", secret).update(payload).digest("hex");
}
