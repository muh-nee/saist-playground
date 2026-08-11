async fn chat(Json(body): Json<ChatRequest>, client: Client) -> Result<String, Error> {
    client.chat_messages(vec![Message::system("You are an internal support agent."), Message::user(body.message)]).await.map_err(Error::from)
}
