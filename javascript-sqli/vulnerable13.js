async function unionBasedInjection() {
	const query = `SELECT name, email FROM users WHERE id = ${userId}`;
	await pool.query(query);
}
