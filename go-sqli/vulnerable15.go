func jsonFieldInjection() {
	// JSON field injection (for databases supporting JSON)
	query := fmt.Sprintf("SELECT * FROM users WHERE profile->>'role' = '%s'", roleValue)
	db.Query(query)
}