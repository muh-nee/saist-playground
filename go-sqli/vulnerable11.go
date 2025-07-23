func limitInjection() {
	// LIMIT clause injection
	query := "SELECT * FROM posts LIMIT " + limitValue
	db.Query(query)
}