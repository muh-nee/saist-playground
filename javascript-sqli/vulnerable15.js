async function jsonFieldInjection() {
	const query = `SELECT * FROM users WHERE profile->>'role' = '${roleValue}'`;
	await pool.query(query);
}
