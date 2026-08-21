use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestMessage}};
use axum::{extract::Query, response::IntoResponse};
use std::collections::HashMap;
use std::process::Command;

pub async fn setup_deps(Query(params): Query<HashMap<String, String>>) -> impl IntoResponse {
    let feature = params.get("feature").cloned().unwrap_or_default();
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4")
        .messages([ChatCompletionRequestMessage::User(
            ChatCompletionRequestUserMessageArgs::default()
                .content(format!("List Rust crates for: {}. One crate name per line.", feature))
                .build().unwrap(),
        )])
        .build().unwrap();
    let response = client.chat().create(request).await.unwrap();
    let content = response.choices[0].message.content.clone().unwrap_or_default();
    for line in content.lines() {
        let crate_name = line.trim();
        if !crate_name.is_empty() {
            Command::new("cargo").args(&["install", crate_name]).output().unwrap();
        }
    }
    "done"
}
