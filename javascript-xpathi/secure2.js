const xpath = require("xpath");

const ALLOWED_CATEGORIES = new Set(["books", "electronics", "clothing"]);

function searchProducts(category) {
	if (!ALLOWED_CATEGORIES.has(category)) return [];
	return xpath.select(`//product[category='${category}']`, catalogDoc);
}
