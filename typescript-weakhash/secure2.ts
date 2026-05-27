import * as argon2 from "argon2";
import * as crypto from "crypto";

export async function storePassword(password: string): Promise<string> {
	return argon2.hash(password);
}

export function checksum(data: string | Buffer): string {
	return crypto.createHash("sha256").update(data).digest("hex");
}
