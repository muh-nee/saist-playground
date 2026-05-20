const xpath = require("xpath");
const express = require("express");
const app = express();

app.post("/login", (req, res) => {
	const query = `//user[name/text()='${req.body.username}' and password/text()='${req.body.password}']`;
	const node = xpath.select(query, doc);
	res.json({ authenticated: !!node });
});
