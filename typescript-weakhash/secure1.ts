import * as bcrypt from "bcrypt";
import * as crypto from "crypto";

export async function storePassword(password: string): Promise<string> {
	return bcrypt.hash(password, 12);
}

export function signToken(payload: string, secret: string): string {
	return crypto.createHmac("sha256", secret).update(payload).digest("hex");
}
