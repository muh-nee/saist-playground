const express = require("express");
const app = express();

app.post("/login", (req, res) => {
	res.cookie("sessionId", req.user.id, {
		httpOnly: true,
		secure: true,
		sameSite: "strict",
	});
	res.sendStatus(200);
});
