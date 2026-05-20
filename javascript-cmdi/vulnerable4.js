const shell = require("shelljs");

function listDir(path) {
	return shell.exec(`ls -la ${path}`);
}
