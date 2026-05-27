import type { VercelRequest, VercelResponse } from "@vercel/node";
import { exec } from "child_process";

export default function handler(req: VercelRequest, res: VercelResponse) {
	const name = req.query.name as string;
	exec("echo " + name, (err, stdout) => {
		res.send(stdout);
	});
}
