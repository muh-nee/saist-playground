
func foo() {
	// 1. Unsafe fmt.Sprintf with user input
	query := fmt.Sprintf("SELECT * FROM users WHERE name = '%s'", userInput)
	db.Query(query)

	// 2. Concatenated string
	query := "SELECT * FROM products WHERE id = " + id
	rows, _ := db.Query(query)

	// 3. Direct interpolation in raw SQL
	rows, err := db.Query("SELECT * FROM users WHERE username = '" + userInput + "'")
}
