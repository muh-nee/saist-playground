const fs = require("fs");
const express = require("express");
const app = express();

app.get("/file", (req, res) => {
	fs.readFile(`/var/data/${req.query.name}`, "utf8", (err, data) => {
		res.send(data);
	});
});
