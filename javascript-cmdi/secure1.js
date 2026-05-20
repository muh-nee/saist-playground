const { spawn, execFile } = require("child_process");
const express = require("express");
const app = express();

app.get("/ping", (req, res) => {
	execFile("/usr/bin/ping", ["-c", "1", req.query.host], (err, stdout) => {
		res.send(stdout);
	});
});

function listDir(path) {
	return spawn("ls", ["-la", path]);
}
