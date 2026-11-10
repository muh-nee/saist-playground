const crypto = require("crypto");

function encryptUserData(plaintext) {
	const key = Buffer.from(process.env.ENCRYPTION_KEY, "hex");
	const iv = crypto.randomBytes(12);
	const cipher = crypto.createCipheriv("aes-256-gcm", key, iv);
	const ciphertext = Buffer.concat([cipher.update(plaintext, "utf8"), cipher.final()]);
	return { iv, ciphertext, tag: cipher.getAuthTag() };
}
