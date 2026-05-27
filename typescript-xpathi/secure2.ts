import * as xpath from "xpath";

declare const catalogDoc: Node;

const ALLOWED_CATEGORIES: ReadonlySet<string> = new Set(["books", "electronics", "clothing"]);

export function searchProducts(category: string): xpath.SelectedValue[] {
	if (!ALLOWED_CATEGORIES.has(category)) return [];
	return xpath.select(`//product[category='${category}']`, catalogDoc);
}
