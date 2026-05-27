import * as crypto from "crypto";

export function legacyEncrypt(data: Buffer, key: Buffer): Buffer {
	const cipher = crypto.createCipheriv("des-cbc", key, Buffer.alloc(8));
	return Buffer.concat([cipher.update(data), cipher.final()]);
}
