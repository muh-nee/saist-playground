async fn run_agent(Json(body): Json<ChatRequest>, agent: Agent) -> Result<(), Error> {
    for _ in 0..8 { if !agent.needs_another_step().await? { return Ok(()); } agent.run_tool(&body.message).await?; }
    Err(Error::BudgetExhausted)
}
