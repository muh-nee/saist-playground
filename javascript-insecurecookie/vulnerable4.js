function setAuthCookie(res, token) {
	res.cookie("auth", token, { httpOnly: true, secure: true });
}
