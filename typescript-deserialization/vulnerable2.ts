import * as yaml from "js-yaml";
import express, { Request, Response } from "express";

interface ConfigBody {
	yaml: string;
}

const app = express();

app.post("/config", (req: Request<unknown, unknown, ConfigBody>, res: Response) => {
	const config = yaml.load(req.body.yaml, { schema: yaml.DEFAULT_FULL_SCHEMA });
	res.json(config);
});
