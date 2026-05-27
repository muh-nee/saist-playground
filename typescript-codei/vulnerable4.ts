import express, { Request, Response } from "express";

interface ScheduleBody {
	callback: string;
}

const app = express();

app.post("/schedule", (req: Request<unknown, unknown, ScheduleBody>, res: Response) => {
	setTimeout(req.body.callback, 1000);
	res.sendStatus(202);
});
