function attachUserContext(req) {
	req.context = {
		serverTime: Date.now(),
		...req.body,
		userId: req.session.userId,
	};
}
