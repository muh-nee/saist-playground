const ldap = require("ldapjs");
const client = ldap.createClient({ url: "ldap://corp.example.com" });

function searchByEmail(email) {
	const filter = "(mail=" + email + ")";
	client.search("dc=corp", { filter });
}
