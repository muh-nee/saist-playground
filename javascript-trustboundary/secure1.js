const express = require("express");
const app = express();

app.post("/profile", (req, res) => {
	req.session.user = {
		displayName: String(req.body.displayName || "").slice(0, 80),
		bio: String(req.body.bio || "").slice(0, 500),
	};
	res.sendStatus(200);
});
