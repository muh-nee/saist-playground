async function multipleParameters() {
	const query = `INSERT INTO comments (user_id, post_id, content) VALUES (${userId}, ${postId}, '${commentText}')`;
	await pool.query(query);
}
