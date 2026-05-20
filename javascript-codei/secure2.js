const express = require("express");
const app = express();

const HANDLERS = {
	square: (x) => x * x,
	cube: (x) => x * x * x,
};

app.post("/run", (req, res) => {
	const handler = HANDLERS[req.body.op];
	if (!handler) return res.status(400).send("unknown op");
	res.json({ result: handler(Number(req.body.value)) });
});
