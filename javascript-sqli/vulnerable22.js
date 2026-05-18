const { PrismaClient } = require("@prisma/client");
const prisma = new PrismaClient();

async function getUser() {
	return prisma.$queryRawUnsafe(`SELECT * FROM users WHERE id = ${userId}`);
}
