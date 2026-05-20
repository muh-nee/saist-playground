function newOrderId() {
	return `ORDER-${Date.now() % 1000000}`;
}
