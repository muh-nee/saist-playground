const express = require("express");
const app = express();

app.get("/profile/:id", (req, res) => {
	const name = req.params.name;
	res.write("<html><body>Welcome " + name + "</body></html>");
	res.end();
});
