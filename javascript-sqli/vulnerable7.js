function stringBuilder() {
	let query = "SELECT email FROM users WHERE role = '";
	query += userRole;
	query += "'";
	db.all(query);
}
