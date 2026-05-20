function Comment({ comment }) {
	return <div>{comment.body}</div>;
}

function renderText(el, value) {
	el.innerText = value;
}
