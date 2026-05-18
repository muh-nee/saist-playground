async function safeMysql2() {
	const mysql = require("mysql2/promise");
	const pool = mysql.createPool(config);
	const [rows] = await pool.execute("SELECT * FROM users WHERE email = ?", [email]);
	return rows;
}

async function safePgPromise() {
	const pgp = require("pg-promise")();
	const db = pgp(connectionString);
	return db.any("SELECT * FROM orders WHERE customer_id = $1", [customerId]);
}

function safeBetterSqlite3() {
	const Database = require("better-sqlite3");
	const db = new Database("app.db");
	const stmt = db.prepare("SELECT * FROM products WHERE name LIKE ?");
	return stmt.all(`%${query}%`);
}
