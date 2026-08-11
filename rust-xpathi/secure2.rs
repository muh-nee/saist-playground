fn select_category(Query(query): Query<CategoryQuery>, factory: Factory) -> Result<(), Error> {
    let expression = match query.category.as_str() { "books" => "//book", "authors" => "//author", _ => return Err(Error::InvalidCategory) };
    factory.build(expression)?.ok_or(Error::InvalidExpression)?;
    Ok(())
}
