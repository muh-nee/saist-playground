import * as ldap from "ldapjs";

const client = ldap.createClient({ url: "ldap://corp.example.com" });

export function searchByEmail(email: string): void {
	const filter = "(mail=" + email + ")";
	client.search("dc=corp", { filter });
}
