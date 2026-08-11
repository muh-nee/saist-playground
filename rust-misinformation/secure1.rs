async fn answer(Json(body): Json<Question>, client: Client) -> Result<impl IntoResponse, Error> {
    let answer = client.chat(body.question).await?;
    Ok(Json(Answer { text: answer, disclaimer: "AI-generated content. Verify independently.", citations: retrieve_sources(&body.question).await? }))
}
