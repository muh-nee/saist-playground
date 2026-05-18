const Database = require("better-sqlite3");
const db = new Database("app.db");

function search() {
	const stmt = db.prepare(`SELECT * FROM products WHERE name LIKE '%${query}%'`);
	return stmt.all();
}
