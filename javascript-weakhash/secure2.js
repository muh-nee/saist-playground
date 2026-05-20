const argon2 = require("argon2");
const crypto = require("crypto");

async function storePassword(password) {
	return argon2.hash(password);
}

function checksum(data) {
	return crypto.createHash("sha256").update(data).digest("hex");
}
