async fn classify(Json(body): Json<Document>, client: Client) -> Result<impl IntoResponse, Error> {
    let label: Label = serde_json::from_str(&client.chat(body.text).await?)?;
    Ok(Json(label))
}
