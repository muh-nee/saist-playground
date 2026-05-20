function Comment({ comment }) {
	return <div dangerouslySetInnerHTML={{ __html: comment.body }} />;
}
