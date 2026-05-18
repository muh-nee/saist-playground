async function qux() {
	const allowedTables = { users: true, products: true, orders: true };
	let safeTable = tableName;
	if (!allowedTables[safeTable]) {
		safeTable = "users";
	}
	const q1 = `SELECT * FROM ${safeTable} WHERE active = 1`;
	connection.query(q1);

	const q2 = "SELECT * FROM products WHERE category_id IN (SELECT id FROM categories WHERE name = $1)";
	await pool.query(q2, [categoryName]);

	const q3 = "SELECT name, email FROM users WHERE id = $1";
	await pool.query(q3, [userId]);

	const q4 = "CALL GetUserData(?, ?)";
	connection.query(q4, [username, roleFilter]);

	const q5 = "SELECT * FROM users WHERE profile->>'role' = $1";
	await pool.query(q5, [roleValue]);
}
