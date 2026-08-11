async fn summarize(Json(body): Json<Document>, agent: Agent) -> Result<impl IntoResponse, Error> {
    Ok(Json(agent.prompt(&body.text).await?))
}
