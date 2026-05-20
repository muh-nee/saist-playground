const express = require("express");
const db = require("./db");
const app = express();

app.post("/promote/:userId", async (req, res) => {
	const actor = await db.findUser(req.session.userId);
	if (actor.role !== "admin") return res.sendStatus(403);
	const target = await db.findUser(req.params.userId);
	req.session.lastPromotionActor = actor.id;
	target.role = "admin";
	await db.saveUser(target);
	res.sendStatus(204);
});
