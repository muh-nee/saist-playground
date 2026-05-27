export function newOrderId(): string {
	return `ORDER-${Date.now() % 1000000}`;
}
