func qux() {
	// 1. Validate table names against whitelist
	allowedTables := map[string]bool{"users": true, "products": true, "orders": true}
	if !allowedTables[tableName] {
		tableName = "users" // default safe table
	}
	query := fmt.Sprintf("SELECT * FROM %s WHERE active = 1", tableName)
	db.Query(query)

	// 2. Use parameterized subquery
	query := "SELECT * FROM products WHERE category_id IN (SELECT id FROM categories WHERE name = ?)"
	db.Query(query, categoryName)

	// 3. Parameterized query for UNION prevention
	query := "SELECT name, email FROM users WHERE id = ?"
	db.Query(query, userId)

	// 4. Use prepared statements for stored procedures
	query := "CALL GetUserData(?, ?)"
	db.Exec(query, username, roleFilter)

	// 5. Parameterized JSON field queries
	query := "SELECT * FROM users WHERE profile->>'role' = ?"
	db.Query(query, roleValue)
}