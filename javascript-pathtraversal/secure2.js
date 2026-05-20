const fs = require("fs");
const express = require("express");
const app = express();

const ALLOWED_FILES = new Set(["report.pdf", "summary.csv", "data.json"]);

app.get("/file", (req, res) => {
	if (!ALLOWED_FILES.has(req.query.name)) {
		return res.status(400).send("invalid file");
	}
	fs.readFile(`/var/data/${req.query.name}`, "utf8", (err, data) => res.send(data));
});
