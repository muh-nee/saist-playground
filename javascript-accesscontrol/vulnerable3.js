const express = require("express");
const app = express();

app.get("/dashboard", (req, res) => {
	if (req.headers["x-role"] === "admin") {
		return res.json(db.getAdminDashboard());
	}
	res.sendStatus(403);
});
