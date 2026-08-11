async fn search(client: Client, prompt: String, pool: PgPool) -> Result<(), Error> {
    let result = client.chat(prompt).await?;
    let request: SearchRequest = serde_json::from_str(&result)?;
    let term = validate_search_term(request.term)?;
    sqlx::query("SELECT * FROM documents WHERE title = $1").bind(term).fetch_all(&pool).await?;
    Ok(())
}
