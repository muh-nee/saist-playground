const path = require("path");
const express = require("express");
const app = express();

app.get("/static/:file", (req, res) => {
	res.sendFile(path.join(__dirname, "uploads", req.params.file));
});
