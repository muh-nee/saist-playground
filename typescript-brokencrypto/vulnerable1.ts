import * as crypto from "crypto";

export function encryptEcb(plaintext: Buffer, key: Buffer): Buffer {
	const cipher = crypto.createCipheriv("aes-128-ecb", key, null);
	return Buffer.concat([cipher.update(plaintext), cipher.final()]);
}
