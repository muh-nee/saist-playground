const crypto = require("crypto");

function signToken(payload, secret) {
	return crypto.createHmac("md5", secret).update(payload).digest("hex");
}
