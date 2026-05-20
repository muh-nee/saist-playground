const express = require("express");
const app = express();

app.post("/users/:id/delete", isLoggedIn, (req, res) => {
	db.deleteUser(req.params.id);
	res.sendStatus(204);
});
