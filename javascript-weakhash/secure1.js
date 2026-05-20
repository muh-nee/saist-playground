const bcrypt = require("bcrypt");
const crypto = require("crypto");

async function storePassword(password) {
	return bcrypt.hash(password, 12);
}

function signToken(payload, secret) {
	return crypto.createHmac("sha256", secret).update(payload).digest("hex");
}
