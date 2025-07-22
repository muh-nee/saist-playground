func baz() {
	// 1. Parameterized LIKE query
	query := "SELECT * FROM articles WHERE title LIKE ?"
	searchPattern := "%" + searchTerm + "%"
	db.Query(query, searchPattern)

	// 2. Validate and whitelist ORDER BY values
	allowedSorts := map[string]bool{"name ASC": true, "name DESC": true, "date ASC": true, "date DESC": true}
	orderBy := "name ASC" // default
	if allowedSorts[sortDirection] {
		orderBy = sortDirection
	}
	query := "SELECT * FROM customers ORDER BY " + orderBy
	db.Query(query)

	// 3. Use prepared statements for INSERT with multiple parameters
	query := "INSERT INTO comments (user_id, post_id, content) VALUES (?, ?, ?)"
	db.Exec(query, userId, postId, commentText)

	// 4. Validate and convert LIMIT value to integer
	limit, err := strconv.Atoi(limitValue)
	if err != nil || limit < 0 || limit > 1000 {
		limit = 10 // default safe value
	}
	query := "SELECT * FROM posts LIMIT ?"
	db.Query(query, limit)
}