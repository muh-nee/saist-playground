import * as React from "react";

interface CommentProps {
	comment: { body: string };
}

export function Comment({ comment }: CommentProps) {
	return <div>{comment.body}</div>;
}

export function renderText(el: HTMLElement, value: string): void {
	el.innerText = value;
}
