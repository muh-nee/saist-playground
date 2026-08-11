fn search(term: String, factory: Factory) -> Result<(), Error> {
    let expression = "//book[contains(title, '".to_string() + &term + "')]";
    factory.build(&expression)?.ok_or(Error::InvalidExpression)?;
    Ok(())
}
