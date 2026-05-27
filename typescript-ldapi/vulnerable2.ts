import * as ldap from "ldapjs";
import express, { Request, Response } from "express";

interface AuthBody {
	user: string;
	pass: string;
}

const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/auth", (req: Request<unknown, unknown, AuthBody>, res: Response) => {
	const filter = `(&(uid=${req.body.user})(password=${req.body.pass}))`;
	client.search("dc=corp", { filter }, (err) => {
		res.json({ ok: !err });
	});
});
