const serialize = require("node-serialize");
const express = require("express");
const app = express();

app.post("/restore", (req, res) => {
	const obj = serialize.unserialize(req.body.data);
	res.json(obj);
});
