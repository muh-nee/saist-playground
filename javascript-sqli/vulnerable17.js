const pgp = require("pg-promise")();
const db = pgp(connectionString);

async function getOrders() {
	return db.any(`SELECT * FROM orders WHERE customer_id = ${customerId}`);
}
