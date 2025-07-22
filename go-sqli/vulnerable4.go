func qux() {
	// 1. Dynamic table/column name injection
	tableName := getUserInput()
	query := fmt.Sprintf("SELECT * FROM %s WHERE active = 1", tableName)
	db.Query(query)

	// 2. Subquery injection
	subquery := fmt.Sprintf("(SELECT id FROM categories WHERE name = '%s')", categoryName)
	query := "SELECT * FROM products WHERE category_id IN " + subquery
	db.Query(query)

	// 3. UNION-based injection vulnerability
	query := fmt.Sprintf("SELECT name, email FROM users WHERE id = %s", userId)
	db.Query(query)

	// 4. Stored procedure call with injection
	procCall := fmt.Sprintf("CALL GetUserData('%s', %s)", username, roleFilter)
	db.Exec(procCall)

	// 5. JSON field injection (for databases supporting JSON)
	query := fmt.Sprintf("SELECT * FROM users WHERE profile->>'role' = '%s'", roleValue)
	db.Query(query)
}