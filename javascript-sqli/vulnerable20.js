const knex = require("knex")(config);

async function getByStatus() {
	return knex.raw(`SELECT * FROM tickets WHERE status = '${status}'`);
}
