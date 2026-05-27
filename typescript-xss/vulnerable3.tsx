import * as React from "react";

interface CommentProps {
	comment: { body: string };
}

export function Comment({ comment }: CommentProps) {
	return <div dangerouslySetInnerHTML={{ __html: comment.body }} />;
}
