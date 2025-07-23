func subqueryInjection() {
	// Subquery injection
	subquery := fmt.Sprintf("(SELECT id FROM categories WHERE name = '%s')", categoryName)
	query := "SELECT * FROM products WHERE category_id IN " + subquery
	db.Query(query)
}