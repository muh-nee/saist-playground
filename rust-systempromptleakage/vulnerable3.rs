async fn failed_chat(client: Client) -> impl IntoResponse {
    let system_prompt = "Internal assistant instructions";
    let request = ChatRequest::new().system(system_prompt).user("hello");
    match client.send(request).await { Ok(_) => StatusCode::NO_CONTENT.into_response(), Err(err) => (StatusCode::BAD_GATEWAY, format!("{err:?}; system={system_prompt}")).into_response() }
}
