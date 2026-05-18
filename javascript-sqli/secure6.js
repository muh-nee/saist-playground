async function safeSequelize() {
	const { Sequelize, QueryTypes } = require("sequelize");
	const sequelize = new Sequelize(dbUrl);
	return sequelize.query("SELECT * FROM users WHERE username = ?", {
		replacements: [username],
		type: QueryTypes.SELECT,
	});
}

async function safeKnex() {
	const knex = require("knex")(config);
	return knex.raw("SELECT * FROM tickets WHERE status = ?", [status]);
}

async function safeTypeorm() {
	const { getConnection } = require("typeorm");
	const conn = getConnection();
	return conn.query("SELECT * FROM products WHERE category = $1", [category]);
}

async function safePrisma() {
	const { PrismaClient } = require("@prisma/client");
	const prisma = new PrismaClient();
	return prisma.$queryRaw`SELECT * FROM users WHERE id = ${userId}`;
}
