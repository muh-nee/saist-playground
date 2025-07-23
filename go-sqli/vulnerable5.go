func stringConcatenation() {
	// Concatenated string vulnerability
	query := "SELECT * FROM products WHERE id = " + id
	rows, _ := db.Query(query)
}