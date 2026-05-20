const yaml = require("js-yaml");
const express = require("express");
const app = express();

app.post("/config", (req, res) => {
	const config = yaml.load(req.body.yaml, { schema: yaml.DEFAULT_FULL_SCHEMA });
	res.json(config);
});
