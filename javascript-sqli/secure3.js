function baz() {
	const q1 = "SELECT * FROM articles WHERE title LIKE ?";
	const searchPattern = "%" + searchTerm + "%";
	db.all(q1, [searchPattern]);

	const allowedSorts = { "name ASC": true, "name DESC": true, "date ASC": true, "date DESC": true };
	let orderBy = "name ASC";
	if (allowedSorts[sortDirection]) {
		orderBy = sortDirection;
	}
	const q2 = "SELECT * FROM customers ORDER BY " + orderBy;
	db.all(q2);

	const q3 = "INSERT INTO comments (user_id, post_id, content) VALUES (?, ?, ?)";
	db.run(q3, [userId, postId, commentText]);

	let limit = parseInt(limitValue, 10);
	if (Number.isNaN(limit) || limit < 0 || limit > 1000) {
		limit = 10;
	}
	const q4 = "SELECT * FROM posts LIMIT ?";
	db.all(q4, [limit]);
}
