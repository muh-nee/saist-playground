import { z } from "zod";
import express, { Request, Response } from "express";

const ProfileSchema = z.object({
	name: z.string().max(80),
	age: z.number().int().min(0).max(150),
});

type Profile = z.infer<typeof ProfileSchema>;

const app = express();

app.post("/profile", (req: Request, res: Response<Profile>) => {
	const profile = ProfileSchema.parse(req.body);
	res.json(profile);
});
