const fs = require("fs");
const express = require("express");
const app = express();

app.get("/download", (req, res) => {
	fs.createReadStream(req.query.path).pipe(res);
});
