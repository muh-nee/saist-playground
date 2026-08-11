async fn failed_chat(client: Client) -> impl IntoResponse {
    let system_prompt = "Internal assistant instructions";
    match client.send(ChatRequest::new().system(system_prompt).user("hello")).await { Ok(_) => StatusCode::NO_CONTENT.into_response(), Err(err) => { tracing::error!(?err, "chat failed"); (StatusCode::BAD_GATEWAY, "model request failed").into_response() } }
}
