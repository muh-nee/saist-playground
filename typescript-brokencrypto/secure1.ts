import * as crypto from "crypto";

interface Encrypted {
	iv: Buffer;
	ciphertext: Buffer;
	tag: Buffer;
}

export function encrypt(plaintext: string, key: Buffer): Encrypted {
	const iv = crypto.randomBytes(12);
	const cipher = crypto.createCipheriv("aes-256-gcm", key, iv);
	const ciphertext = Buffer.concat([cipher.update(plaintext, "utf8"), cipher.final()]);
	const tag = cipher.getAuthTag();
	return { iv, ciphertext, tag };
}
