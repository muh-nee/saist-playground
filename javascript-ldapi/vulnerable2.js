const ldap = require("ldapjs");
const express = require("express");
const app = express();
const client = ldap.createClient({ url: "ldap://corp.example.com" });

app.post("/auth", (req, res) => {
	const filter = `(&(uid=${req.body.user})(password=${req.body.pass}))`;
	client.search("dc=corp", { filter }, (err, result) => {
		res.json({ ok: !err });
	});
});
