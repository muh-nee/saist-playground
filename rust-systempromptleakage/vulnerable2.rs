async fn chat_and_debug(client: Client) -> Result<impl IntoResponse, Error> {
    let system_message = std::env::var("SYSTEM_PROMPT")?;
    let request = ChatRequest::new().system(&system_message).user("hello");
    client.send(request.clone()).await?;
    Ok(Json(request))
}
