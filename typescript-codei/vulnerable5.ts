import express, { Request, Response } from "express";
import _ from "lodash";

interface RenderBody {
	template: string;
}

const app = express();

app.post("/render", (req: Request<unknown, unknown, RenderBody>, res: Response) => {
	const compiled = _.template(req.body.template);
	res.json({ result: compiled({}) });
});
