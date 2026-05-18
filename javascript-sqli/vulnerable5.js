async function stringConcatenation() {
	const query = "SELECT * FROM products WHERE id = " + id;
	const rows = await pool.query(query);
}
