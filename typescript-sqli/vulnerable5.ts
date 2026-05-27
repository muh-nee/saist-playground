type SearchOptions = {
	keyword: string;
	limit?: number;
};

async function search(opts: SearchOptions): Promise<unknown[]> {
	const limit = opts.limit ?? 10;
	const sql = "SELECT * FROM articles WHERE title LIKE '%" + opts.keyword + "%' LIMIT " + limit;
	return connection.query(sql);
}
