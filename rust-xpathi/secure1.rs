fn find_user(Query(query): Query<UserQuery>, factory: Factory) -> Result<(), Error> {
    if !query.name.chars().all(|c| c.is_ascii_alphanumeric()) { return Err(Error::InvalidInput); }
    factory.build(&format!("//user[name='{}']", query.name))?.ok_or(Error::InvalidExpression)?;
    Ok(())
}
