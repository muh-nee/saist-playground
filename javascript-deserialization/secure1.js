const yaml = require("js-yaml");
const express = require("express");
const app = express();

app.post("/config", (req, res) => {
	const config = yaml.load(req.body.yaml);
	res.json(config);
});

app.post("/restore", (req, res) => {
	const obj = JSON.parse(req.body.data);
	res.json(obj);
});
