const { execSync } = require("child_process");

function archive(filename) {
	return execSync(`tar -czf /tmp/archive.tar.gz ${filename}`);
}
