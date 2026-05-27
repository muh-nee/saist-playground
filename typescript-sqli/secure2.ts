interface UserFilter {
	name: string;
	role: string;
}

class UserRepository {
	async findByFilter(filter: UserFilter): Promise<unknown[]> {
		return connection.query(
			"SELECT * FROM users WHERE name = ? AND role = ?",
			[filter.name, filter.role],
		);
	}
}
