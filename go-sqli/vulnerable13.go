func unionBasedInjection() {
	// UNION-based injection vulnerability
	query := fmt.Sprintf("SELECT name, email FROM users WHERE id = %s", userId)
	db.Query(query)
}