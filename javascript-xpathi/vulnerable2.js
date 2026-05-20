const xpath = require("xpath");

function findUser(username) {
	return xpath.select(`//users/user[name='${username}']`, doc);
}
