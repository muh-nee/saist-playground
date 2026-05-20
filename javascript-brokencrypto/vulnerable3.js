const crypto = require("crypto");

function encryptToken(token, key) {
	const iv = Buffer.from("00000000000000000000000000000000", "hex");
	const cipher = crypto.createCipheriv("aes-256-cbc", key, iv);
	return cipher.update(token, "utf8", "hex") + cipher.final("hex");
}
