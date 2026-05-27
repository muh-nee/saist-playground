import * as crypto from "crypto";

export function encryptToken(token: string, key: Buffer): string {
	const iv = Buffer.from("00000000000000000000000000000000", "hex");
	const cipher = crypto.createCipheriv("aes-256-cbc", key, iv);
	return cipher.update(token, "utf8", "hex") + cipher.final("hex");
}
