async fn assist(Query(query): Query<AssistantQuery>, agent: Agent) -> Result<String, Error> {
    agent.preamble(format!("You are a helpful assistant with role {}", query.role)).prompt(query.message).await.map_err(Error::from)
}
