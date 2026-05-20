const ldap = require("ldapjs");
const ldapEscape = require("ldap-escape");
const express = require("express");
const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/find-user", (req, res) => {
	const filter = `(uid=${ldapEscape.filter`${req.body.username}`})`;
	client.search("ou=users,dc=corp,dc=example,dc=com", { filter }, (err) => {
		res.json({ ok: !err });
	});
});
