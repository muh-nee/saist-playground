function limitInjection() {
	const query = "SELECT * FROM posts LIMIT " + limitValue;
	db.all(query);
}
