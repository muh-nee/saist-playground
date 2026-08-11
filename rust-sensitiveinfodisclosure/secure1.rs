async fn troubleshoot(Json(body): Json<Request>, client: Client) -> Result<String, Error> {
    client.chat(format!("Debug this issue without requesting secrets: {}", body.issue)).await.map_err(Error::from)
}
