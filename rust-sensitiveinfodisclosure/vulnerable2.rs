async fn explain_user(user: CurrentUser, Path(id): Path<i64>, client: Client, pool: PgPool) -> Result<String, Error> {
    let record = sqlx::query_as!(Customer, "SELECT * FROM customers WHERE id = $1", id).fetch_one(&pool).await?;
    client.chat(format!("Explain this customer record: {record:?}")).await.map_err(Error::from)
}
