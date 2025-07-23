func stringBuilder() {
	// String building with user input
	var queryBuilder strings.Builder
	queryBuilder.WriteString("SELECT email FROM users WHERE role = '")
	queryBuilder.WriteString(userRole)
	queryBuilder.WriteString("'")
	db.Query(queryBuilder.String())
}