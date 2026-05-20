const { z } = require("zod");
const express = require("express");
const app = express();

const ProfileSchema = z.object({
	name: z.string().max(80),
	age: z.number().int().min(0).max(150),
});

app.post("/profile", (req, res) => {
	const profile = ProfileSchema.parse(req.body);
	res.json(profile);
});
