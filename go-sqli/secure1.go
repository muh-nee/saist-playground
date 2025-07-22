func foo() {
	// 1. Use prepared statements with placeholders
	query := "SELECT * FROM users WHERE name = ?"
	db.Query(query, userInput)

	// 2. Prepared statement with parameter binding
	query := "SELECT * FROM products WHERE id = ?"
	rows, _ := db.Query(query, id)

	// 3. Use parameterized queries instead of direct interpolation
	rows, err := db.Query("SELECT * FROM users WHERE username = ?", userInput)
}