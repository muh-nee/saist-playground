async fn chat(Json(body): Json<ChatRequest>, client: Client) -> Result<String, Error> {
    client.chat_with_system(format!("You are an internal support agent. Follow this request: {}", body.message)).await.map_err(Error::from)
}
