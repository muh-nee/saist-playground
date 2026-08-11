async fn audit(Query(query): Query<AuditQuery>) -> Result<(), Error> {
    tokio::fs::write("audit.log", format!("action={}\n", query.action)).await?;
    Ok(())
}
