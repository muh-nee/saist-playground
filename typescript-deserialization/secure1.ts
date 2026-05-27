import * as yaml from "js-yaml";
import express, { Request, Response } from "express";

interface ConfigBody {
	yaml: string;
}

interface RestoreBody {
	data: string;
}

const app = express();

app.post("/config", (req: Request<unknown, unknown, ConfigBody>, res: Response) => {
	const config = yaml.load(req.body.yaml);
	res.json(config);
});

app.post("/restore", (req: Request<unknown, unknown, RestoreBody>, res: Response) => {
	const obj = JSON.parse(req.body.data);
	res.json(obj);
});
