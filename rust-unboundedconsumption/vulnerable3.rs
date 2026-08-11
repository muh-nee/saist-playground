async fn stream(Json(body): Json<ChatRequest>, client: Client) -> Result<String, Error> {
    let mut response = String::new();
    let mut stream = client.stream(body.message).await?;
    while let Some(token) = stream.next().await { response.push_str(&token?); }
    Ok(response)
}
