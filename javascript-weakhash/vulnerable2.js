const crypto = require("crypto");

function checksum(data) {
	return crypto.createHash("sha1").update(data).digest("hex");
}
