async fn find_user(Query(query): Query<UserQuery>, pool: PgPool) -> Result<(), Error> {
    let sql = format!("SELECT * FROM users WHERE name = '{}'", query.name);
    sqlx::query(&sql).fetch_all(&pool).await?;
    Ok(())
}
