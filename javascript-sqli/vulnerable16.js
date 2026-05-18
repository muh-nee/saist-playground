const mysql = require("mysql2/promise");

async function lookup() {
	const pool = mysql.createPool(config);
	const [rows] = await pool.execute(`SELECT * FROM users WHERE email = '${email}'`);
	return rows;
}
