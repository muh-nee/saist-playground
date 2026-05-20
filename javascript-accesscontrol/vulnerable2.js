const express = require("express");
const app = express();

app.get("/orders/:userId", (req, res) => {
	const orders = db.findOrdersByUserId(req.params.userId);
	res.json(orders);
});
