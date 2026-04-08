
func foo() {
	query := fmt.Sprintf("SELECT * FROM users_table WHERE name = '%s'", userInput)
	db.Query(query)
}
