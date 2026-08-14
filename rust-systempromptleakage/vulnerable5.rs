use async_openai::{
    types::{
        ChatCompletionRequestSystemMessageArgs, ChatCompletionRequestUserMessageArgs,
        CreateChatCompletionRequestArgs,
    },
    Client,
};
use axum::{extract::{Query, State}, response::Json};
use std::collections::HashMap;

async fn context_handler(
    State(client): State<Client>,
    Query(params): Query<HashMap<String, String>>,
) -> Json<serde_json::Value> {
    let query = params.get("q").cloned().unwrap_or_default();
    let policy_text = retrieve_policy_docs(&query).await.join("\n");
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(vec![
            ChatCompletionRequestSystemMessageArgs::default()
                .content(policy_text.clone())
                .build()
                .unwrap()
                .into(),
            ChatCompletionRequestUserMessageArgs::default()
                .content(query)
                .build()
                .unwrap()
                .into(),
        ])
        .build()
        .unwrap();
    client.chat().create(request).await.unwrap();
    Json(serde_json::json!({ "policy": policy_text }))
}

async fn retrieve_policy_docs(query: &str) -> Vec<String> {
    vec![format!("Internal policy for query: {}", query)]
}
