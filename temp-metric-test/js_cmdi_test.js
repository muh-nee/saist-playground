const { exec } = require("child_process");
const express = require("express");
const app = express();

app.get("/ping", (req, res) => {
	exec(`ping -c 1 ${req.query.host}`, (err, stdout) => {
		res.send(stdout);
	});
});
