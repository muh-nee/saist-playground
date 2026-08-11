async fn complete(Json(body): Json<ChatRequest>, client: Client) -> Result<String, Error> {
    if body.message.len() > 8_000 { return Err(Error::InputTooLarge); }
    tokio::time::timeout(Duration::from_secs(20), client.chat_with_max_tokens(body.message, 512)).await??.pipe(Ok)
}
