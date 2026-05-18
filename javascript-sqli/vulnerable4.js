function qux() {
	const tableName = getUserInput();
	const query = `SELECT * FROM ${tableName} WHERE active = 1`;
	connection.query(query);
}
