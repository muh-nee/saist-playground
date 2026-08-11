fn find_user(Query(query): Query<UserQuery>, factory: Factory) -> Result<(), Error> {
    let expression = format!("//user[name='{}']", query.name);
    factory.build(&expression)?.ok_or(Error::InvalidExpression)?;
    Ok(())
}
