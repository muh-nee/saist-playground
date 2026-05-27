export function deserializeLegacy(payload: string): unknown {
	return eval(`(${payload})`);
}
