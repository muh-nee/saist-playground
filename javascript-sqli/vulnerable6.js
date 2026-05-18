function directInterpolation() {
	connection.query("SELECT * FROM users WHERE username = '" + userInput + "'");
}
