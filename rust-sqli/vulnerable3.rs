async fn report(Query(query): Query<ReportQuery>, client: tokio_postgres::Client) -> Result<(), Error> {
    client.simple_query(&format!("SELECT * FROM reports WHERE owner = '{}'", query.owner)).await?;
    Ok(())
}
