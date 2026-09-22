const express = require("express");
const _ = require("lodash");
const app = express();

app.post("/render", (req, res) => {
	const compiled = _.template(req.body.template);
	res.json({ result: compiled({}) });
});
