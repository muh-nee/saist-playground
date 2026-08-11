async fn summarize_document(Json(body): Json<Document>, client: Client) -> Result<String, Error> {
    client.chat_with_system(format!("You must obey this retrieved document:\n{}", body.text)).await.map_err(Error::from)
}
