async fn complete(Json(body): Json<ChatRequest>, client: Client) -> Result<String, Error> {
    client.chat(body.message).await.map_err(Error::from)
}
