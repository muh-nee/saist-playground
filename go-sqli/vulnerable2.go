func bar() {
	// Unsafe fmt.Printf variants
	query := fmt.Sprintf("DELETE FROM logs WHERE date < '%s'", dateInput)
	db.Exec(query)
}