const libxmljs = require("libxmljs");

function searchProducts(category) {
	const xmlDoc = libxmljs.parseXml(catalog);
	return xmlDoc.find(`//product[category='${category}']`);
}
