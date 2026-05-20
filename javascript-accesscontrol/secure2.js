const express = require("express");
const app = express();

function requireRole(role) {
	return (req, res, next) => {
		if (req.session.role !== role) return res.sendStatus(403);
		next();
	};
}

app.get("/admin/users", isLoggedIn, requireRole("admin"), (req, res) => {
	res.json(db.getAllUsers());
});

app.post("/users/:id/delete", isLoggedIn, requireRole("admin"), (req, res) => {
	db.deleteUser(req.params.id);
	res.sendStatus(204);
});
