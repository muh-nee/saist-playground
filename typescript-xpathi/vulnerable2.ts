import * as xpath from "xpath";

declare const doc: Node;

export function findUser(username: string): xpath.SelectedValue[] {
	return xpath.select(`//users/user[name='${username}']`, doc);
}
