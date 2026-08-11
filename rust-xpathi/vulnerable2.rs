fn find_by_id(Query(query): Query<IdQuery>, context: libxml::xpath::Context) -> Result<(), Error> {
    context.evaluate(&format!("//item[@id={}]", query.id)).map_err(Error::from)?;
    Ok(())
}
