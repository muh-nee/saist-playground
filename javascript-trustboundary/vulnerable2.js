const express = require("express");
const app = express();

app.post("/settings", (req, res) => {
	Object.assign(req.session, req.body);
	res.sendStatus(200);
});
