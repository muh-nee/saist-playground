class ProductController {
	@Get("/products/:category")
	async listByCategory(@Param("category") category: string) {
		return connection.query(`SELECT * FROM products WHERE category = '${category}'`);
	}
}
