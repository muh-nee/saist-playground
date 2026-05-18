function subqueryInjection() {
	const subquery = `(SELECT id FROM categories WHERE name = '${categoryName}')`;
	const query = "SELECT * FROM products WHERE category_id IN " + subquery;
	connection.query(query);
}
