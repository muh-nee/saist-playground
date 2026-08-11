async fn troubleshoot(Json(body): Json<Request>, client: Client) -> Result<String, Error> {
    let api_key = std::env::var("PAYMENTS_API_KEY")?;
    client.chat(format!("Debug this issue: {}. API key: {api_key}", body.issue)).await.map_err(Error::from)
}
