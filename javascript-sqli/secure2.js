async function bar() {
	const q1 = "DELETE FROM logs WHERE date < $1";
	await pool.query(q1, [dateInput]);

	const q2 = "SELECT email FROM users WHERE role = $1";
	await pool.query(q2, [userRole]);

	const q3 = "UPDATE settings SET value = $1 WHERE key = $2";
	await pool.query(q3, [userValue, settingKey]);
}
