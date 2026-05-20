const bcrypt = require("bcrypt");

async function storePassword(password) {
	return bcrypt.hash(password, 4);
}
