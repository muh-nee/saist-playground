async fn search(client: Client, prompt: String, pool: PgPool) -> Result<(), Error> {
    let sql = client.chat(prompt).await?;
    sqlx::query(&sql).execute(&pool).await?;
    Ok(())
}
