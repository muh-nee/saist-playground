const { exec } = require("child_process");
const express = require("express");
const app = express();

const allowedHosts = new Set(["host1", "host2", "host3"]);

app.get("/ping", (req, res) => {
	if (!allowedHosts.has(req.query.host)) {
		return res.status(400).send("invalid host");
	}
	exec(`ping -c 1 ${req.query.host}`, (err, stdout) => res.send(stdout));
});
