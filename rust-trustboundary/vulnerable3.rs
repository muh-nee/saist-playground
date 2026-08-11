async fn select_tenant(headers: HeaderMap, session: Session) -> Result<(), Error> {
    session.insert("tenant", headers["x-tenant"].to_str()?)?;
    Ok(())
}
