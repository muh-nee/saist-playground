const express = require("express");
const app = express();

app.get("/orders", isLoggedIn, (req, res) => {
	const orders = db.findOrdersByUserId(req.session.userId);
	res.json(orders);
});

app.get("/orders/:userId", isLoggedIn, (req, res) => {
	if (req.params.userId !== req.session.userId) return res.sendStatus(403);
	const orders = db.findOrdersByUserId(req.params.userId);
	res.json(orders);
});
