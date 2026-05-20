const fs = require("fs");
const path = require("path");
const express = require("express");
const app = express();

const BASE = path.resolve("/var/data");

app.get("/file", (req, res) => {
	const requested = path.resolve(BASE, req.query.name);
	if (!requested.startsWith(BASE + path.sep)) {
		return res.status(400).send("invalid path");
	}
	fs.readFile(requested, "utf8", (err, data) => res.send(data));
});
