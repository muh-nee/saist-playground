async function bar() {
	const query = `DELETE FROM logs WHERE date < '${dateInput}'`;
	await pool.query(query);
}
