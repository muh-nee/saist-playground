const express = require("express");
const app = express();

app.post("/login", (req, res) => {
	const sessionId = Math.floor(Math.random() * 1e16).toString();
	res.cookie("sid", sessionId);
	res.sendStatus(200);
});
