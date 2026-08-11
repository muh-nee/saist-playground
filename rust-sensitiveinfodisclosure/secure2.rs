async fn explain_user(user: CurrentUser, Path(id): Path<i64>, client: Client, pool: PgPool) -> Result<String, Error> {
    authorize_customer_read(&user, id, &pool).await?;
    let record = sqlx::query_as!(CustomerSummary, "SELECT name, plan FROM customers WHERE id = $1", id).fetch_one(&pool).await?;
    client.chat(format!("Explain this customer summary: {record:?}")).await.map_err(Error::from)
}
