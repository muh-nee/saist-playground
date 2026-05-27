import * as libxmljs from "libxmljs";

declare const catalog: string;

export function searchProducts(category: string) {
	const xmlDoc = libxmljs.parseXml(catalog);
	return xmlDoc.find(`//product[category='${category}']`);
}
