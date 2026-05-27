interface UserFilter {
	name: string;
	role: string;
}

class UserRepository {
	async findByFilter(filter: UserFilter): Promise<unknown[]> {
		const sql = `SELECT * FROM users WHERE name = '${filter.name}' AND role = '${filter.role}'`;
		return connection.query(sql);
	}
}
