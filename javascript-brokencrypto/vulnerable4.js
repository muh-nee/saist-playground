const crypto = require("crypto");

function legacyEncrypt(data, key) {
	const cipher = crypto.createCipheriv("des-cbc", key, Buffer.alloc(8));
	return Buffer.concat([cipher.update(data), cipher.final()]);
}
