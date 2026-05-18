const { getConnection } = require("typeorm");

async function listProducts() {
	const conn = getConnection();
	return conn.query(`SELECT * FROM products WHERE category = '${category}'`);
}
