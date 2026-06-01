const express = require("express");
const app = express();

app.get("/search", (req, res) => {
	res.send(`<h1>Results showing for ${req.query.q}</h1>`);
});
