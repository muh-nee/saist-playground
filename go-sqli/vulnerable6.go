func directInterpolation() {
	// Direct interpolation in raw SQL
	rows, err := db.Query("SELECT * FROM users WHERE username = '" + userInput + "'")
}