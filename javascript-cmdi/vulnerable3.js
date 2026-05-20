const { spawn } = require("child_process");
const express = require("express");
const app = express();

app.post("/convert", (req, res) => {
	spawn("convert", [req.body.input, "/tmp/out.png"], { shell: true });
	res.sendStatus(200);
});
