async fn answer(Json(body): Json<Question>, client: Client) -> Result<impl IntoResponse, Error> {
    let answer = client.chat(body.question).await?;
    Ok((StatusCode::OK, answer))
}
