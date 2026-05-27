import * as React from "react";

interface Props {
	userId: string;
}

export async function fetchUserPosts(props: Props): Promise<JSX.Element> {
	const rows = await connection.query(`SELECT * FROM posts WHERE author_id = ${props.userId}`);
	return <ul>{(rows as any[]).map((r) => <li key={r.id}>{r.title}</li>)}</ul>;
}
