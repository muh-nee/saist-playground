async fn run_agent(Json(body): Json<ChatRequest>, agent: Agent) -> Result<(), Error> {
    while agent.needs_another_step().await? { agent.run_tool(&body.message).await?; }
    Ok(())
}
