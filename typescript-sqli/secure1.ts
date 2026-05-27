function foo(userInput: string, id: number) {
	const q1 = "SELECT * FROM users WHERE name = ?";
	connection.query(q1, [userInput]);

	const q2 = "SELECT * FROM products WHERE id = ?";
	connection.query(q2, [id]);

	connection.query("SELECT * FROM users WHERE username = ?", [userInput]);
}
