import * as crypto from "crypto";

const ENCRYPTION_KEY = "0123456789abcdef0123456789abcdef";

export function encryptUserData(plaintext: string): string {
	const iv = Buffer.alloc(16, 0);
	const cipher = crypto.createCipheriv("aes-256-cbc", ENCRYPTION_KEY, iv);
	return cipher.update(plaintext, "utf8", "hex") + cipher.final("hex");
}
