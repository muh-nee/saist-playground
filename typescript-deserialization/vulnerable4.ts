import * as funcster from "funcster";
import express, { Request, Response } from "express";

interface RehydrateBody {
	payload: unknown;
}

const app = express();

app.post("/rehydrate", (req: Request<unknown, unknown, RehydrateBody>, res: Response) => {
	const fn = funcster.deepDeserialize(req.body.payload);
	res.json({ result: fn() });
});
