const express = require("express");
const app = express();

app.post("/schedule", (req, res) => {
	setTimeout(req.body.callback, 1000);
	res.sendStatus(202);
});
