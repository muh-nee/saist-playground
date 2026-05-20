const express = require("express");
const escapeHtml = require("escape-html");
const app = express();

app.get("/search", (req, res) => {
	res.send(`<h1>Results for ${escapeHtml(req.query.q)}</h1>`);
});

function renderProfile(user) {
	document.getElementById("bio").textContent = user.bio;
}
