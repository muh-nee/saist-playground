const crypto = require("crypto");

function newOrderId() {
	return `ORDER-${crypto.randomBytes(8).toString("hex")}`;
}

function apiKey() {
	return crypto.randomBytes(24).toString("base64url");
}
