func qux() {
	// Dynamic table/column name injection
	tableName := getUserInput()
	query := fmt.Sprintf("SELECT * FROM %s WHERE active = 1", tableName)
	db.Query(query)
}