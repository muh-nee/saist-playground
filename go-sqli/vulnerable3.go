func baz() {
	// 1. WHERE clause injection with LIKE operator
	query := fmt.Sprintf("SELECT * FROM articles WHERE title LIKE '%%%s%%'", searchTerm)
	db.Query(query)

	// 2. ORDER BY clause injection
	orderBy := "name " + sortDirection
	query := "SELECT * FROM customers ORDER BY " + orderBy
	db.Query(query)

	// 3. Multiple parameter injection
	query := fmt.Sprintf("INSERT INTO comments (user_id, post_id, content) VALUES (%s, %s, '%s')", 
		userId, postId, commentText)
	db.Exec(query)

	// 4. LIMIT clause injection
	query := "SELECT * FROM posts LIMIT " + limitValue
	db.Query(query)
}