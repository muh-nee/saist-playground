const xpath = require("xpath");
const express = require("express");
const app = express();

app.get("/orders", (req, res) => {
	const expr = "//orders/order[customer='" + req.query.customer + "']";
	const nodes = xpath.select(expr, ordersDoc);
	res.json(nodes.map((n) => n.toString()));
});
