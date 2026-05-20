const crypto = require("crypto");

function encryptEcb(plaintext, key) {
	const cipher = crypto.createCipheriv("aes-128-ecb", key, null);
	return Buffer.concat([cipher.update(plaintext), cipher.final()]);
}
