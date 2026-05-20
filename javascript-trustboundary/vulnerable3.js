const express = require("express");
const app = express();

app.post("/promote", (req, res) => {
	req.session.isAdmin = req.body.isAdmin;
	res.sendStatus(200);
});
