const ldap = require("ldapjs");
const express = require("express");
const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/login", (req, res) => {
	const dn = `uid=${req.body.username},ou=users,dc=corp,dc=example,dc=com`;
	client.bind(dn, req.body.password, (err) => {
		res.json({ ok: !err });
	});
});
