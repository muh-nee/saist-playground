func orderByInjection() {
	// ORDER BY clause injection
	orderBy := "name " + sortDirection
	query := "SELECT * FROM customers ORDER BY " + orderBy
	db.Query(query)
}