function deserializeLegacy(payload) {
	return eval(`(${payload})`);
}
