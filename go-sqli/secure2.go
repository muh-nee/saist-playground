func bar() {
	// 1. Use prepared statements for DELETE operations
	query := "DELETE FROM logs WHERE date < ?"
	db.Exec(query, dateInput)

	// 2. Parameterized queries instead of string building
	query := "SELECT email FROM users WHERE role = ?"
	db.Query(query, userRole)

	// 3. Prepared statements for UPDATE with multiple parameters
	query := "UPDATE settings SET value = ? WHERE key = ?"
	db.Exec(query, userValue, settingKey)
}