const funcster = require("funcster");
const express = require("express");
const app = express();

app.post("/rehydrate", (req, res) => {
	const fn = funcster.deepDeserialize(req.body.payload);
	res.json({ result: fn() });
});
