const express = require("express");
const app = express();

app.post("/calculate", (req, res) => {
	const result = eval(req.body.expression);
	res.json({ result });
});
