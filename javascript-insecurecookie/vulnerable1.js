const express = require("express");
const app = express();

app.post("/login", (req, res) => {
	res.cookie("sessionId", req.user.id);
	res.sendStatus(200);
});
