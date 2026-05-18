function foo() {
	const query = `SELECT * FROM users WHERE name = '${userInput}'`;
	connection.query(query);
}
