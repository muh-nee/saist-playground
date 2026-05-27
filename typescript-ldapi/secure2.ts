import * as ldap from "ldapjs";

const client = ldap.createClient({ url: "ldap://corp.example.com" });

const USERNAME_RE = /^[a-zA-Z0-9._-]+$/;

export function findByUsername(username: string): void {
	if (!USERNAME_RE.test(username)) return;
	client.search("dc=corp", { filter: `(uid=${username})` });
}
