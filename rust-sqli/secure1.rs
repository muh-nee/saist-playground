async fn find_user(Query(query): Query<UserQuery>, pool: PgPool) -> Result<(), Error> {
    sqlx::query("SELECT * FROM users WHERE name = $1").bind(query.name).fetch_all(&pool).await?;
    Ok(())
}
