import * as ldap from "ldapjs";
import express, { Request, Response } from "express";

interface LoginBody {
	username: string;
	password: string;
}

const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/login", (req: Request<unknown, unknown, LoginBody>, res: Response) => {
	const dn = `uid=${req.body.username},ou=users,dc=corp,dc=example,dc=com`;
	client.bind(dn, req.body.password, (err) => {
		res.json({ ok: !err });
	});
});
