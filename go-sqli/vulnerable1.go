
func foo() {
	// Unsafe fmt.Sprintf with user input
	query := fmt.Sprintf("SELECT * FROM users WHERE name = '%s'", userInput)
	db.Query(query)
}
