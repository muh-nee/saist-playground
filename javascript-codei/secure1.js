const express = require("express");
const app = express();

app.post("/calculate", (req, res) => {
	const value = parseFloat(req.body.expression);
	if (!Number.isFinite(value)) return res.status(400).send("invalid");
	res.json({ result: value * 2 });
});
