
func foo() {
	query := fmt.Sprintf("SELECT * FROM userstable WHERE name = '%s'", userInput)
	db.Query(query)
}
