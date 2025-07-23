func baz() {
	// WHERE clause injection with LIKE operator
	query := fmt.Sprintf("SELECT * FROM articles WHERE title LIKE '%%%s%%'", searchTerm)
	db.Query(query)
}