const { Sequelize } = require("sequelize");
const sequelize = new Sequelize(dbUrl);

async function findUser() {
	const [results] = await sequelize.query(`SELECT * FROM users WHERE username = '${username}'`);
	return results;
}
