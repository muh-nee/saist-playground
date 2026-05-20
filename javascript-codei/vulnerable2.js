const express = require("express");
const app = express();

app.post("/run", (req, res) => {
	const fn = new Function(req.body.args, req.body.body);
	res.json({ result: fn() });
});
