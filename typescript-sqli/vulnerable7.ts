declare const prisma: {
  $queryRawUnsafe(query: string): Promise<unknown>;
};

export async function findUser(email: string) {
  return prisma.$queryRawUnsafe(`SELECT * FROM users WHERE email = '${email}'`);
}
