func bar() {
	// 1. Unsafe fmt.Printf variants
	query := fmt.Sprintf("DELETE FROM logs WHERE date < '%s'", dateInput)
	db.Exec(query)

	// 2. String building with user input
	var queryBuilder strings.Builder
	queryBuilder.WriteString("SELECT email FROM users WHERE role = '")
	queryBuilder.WriteString(userRole)
	queryBuilder.WriteString("'")
	db.Query(queryBuilder.String())

	// 3. Template-based query construction
	tmpl := "UPDATE settings SET value = '%s' WHERE key = '%s'"
	query := fmt.Sprintf(tmpl, userValue, settingKey)
	db.Exec(query)
}