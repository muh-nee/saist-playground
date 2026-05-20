const crypto = require("crypto");

function generateResetToken() {
	return crypto.randomBytes(32).toString("hex");
}

function newSessionId() {
	return crypto.randomUUID();
}
