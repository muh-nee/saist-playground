const xpath = require("xpath");
const express = require("express");
const app = express();

const userQuery = xpath.parse("//user[name/text() = $username]");

app.post("/login", (req, res) => {
	const result = userQuery.select({
		node: doc,
		variables: { username: req.body.username },
	});
	res.json({ authenticated: result.length > 0 });
});
