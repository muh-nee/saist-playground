async fn report(Query(query): Query<ReportQuery>, client: tokio_postgres::Client) -> Result<(), Error> {
    client.query("SELECT * FROM reports WHERE owner = $1", &[&query.owner]).await?;
    Ok(())
}
