const fs = require("fs");
const path = require("path");

function loadConfig(name) {
	const fullPath = path.join("/etc/app/", name);
	return fs.readFileSync(fullPath, "utf8");
}
