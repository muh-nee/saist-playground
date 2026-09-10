declare const prisma: {
  sampleStock: {
    update(args: unknown): Promise<unknown>;
  };
};

declare const connection: {
  query(sql: string, values: unknown[]): Promise<unknown>;
};

export async function updateStock(sampleId: string, branchId: string, quantity: number) {
  return prisma.sampleStock.update({
    where: { sampleId_branchId: { sampleId, branchId } },
    data: { quantity },
  });
}

export async function findUser(target: string, value: string) {
  const column = target === "id" ? "id" : "wid";
  return connection.query(`SELECT * FROM users WHERE ${column} = ?`, [value]);
}
