interface User {
	bio: string;
}

export function renderProfile(user: User): void {
	const el = document.getElementById("bio") as HTMLElement;
	el.innerHTML = user.bio;
}
