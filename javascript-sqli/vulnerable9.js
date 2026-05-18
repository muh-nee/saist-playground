function orderByInjection() {
	const orderBy = "name " + sortDirection;
	const query = "SELECT * FROM customers ORDER BY " + orderBy;
	connection.query(query);
}
