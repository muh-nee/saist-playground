const express = require("express");
const app = express();

app.post("/profile", (req, res) => {
	req.session.user = req.body;
	res.sendStatus(200);
});
