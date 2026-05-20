const ldap = require("ldapjs");
const client = ldap.createClient({ url: "ldap://corp.example.com" });

const USERNAME_RE = /^[a-zA-Z0-9._-]+$/;

function findByUsername(username) {
	if (!USERNAME_RE.test(username)) return;
	client.search("dc=corp", { filter: `(uid=${username})` });
}
