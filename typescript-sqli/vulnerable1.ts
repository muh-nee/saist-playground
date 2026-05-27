function foo(userInput: string) {
	const query = `SELECT * FROM users WHERE name = '${userInput}'`;
	connection.query(query);
}
