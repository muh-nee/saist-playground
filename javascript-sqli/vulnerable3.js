function baz() {
	const query = `SELECT * FROM articles WHERE title LIKE '%${searchTerm}%'`;
	db.all(query);
}
