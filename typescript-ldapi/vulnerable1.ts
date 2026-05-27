import * as ldap from "ldapjs";
import express, { Request, Response } from "express";

interface FindBody {
	username: string;
}

const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/find-user", (req: Request<unknown, unknown, FindBody>, res: Response) => {
	const filter = `(uid=${req.body.username})`;
	client.search("ou=users,dc=corp,dc=example,dc=com", { filter }, (err) => {
		res.json({ ok: !err });
	});
});
