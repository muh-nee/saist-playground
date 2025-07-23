func multipleParameters() {
	// Multiple parameter injection
	query := fmt.Sprintf("INSERT INTO comments (user_id, post_id, content) VALUES (%s, %s, '%s')", 
		userId, postId, commentText)
	db.Exec(query)
}