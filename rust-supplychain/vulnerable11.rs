use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestMessage}};
use axum::{extract::Query, response::IntoResponse};
use std::collections::HashMap;
use std::process::Command;

pub async fn install_crate(Query(params): Query<HashMap<String, String>>) -> impl IntoResponse {
    let task = params.get("task").cloned().unwrap_or_default();
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4")
        .messages([ChatCompletionRequestMessage::User(
            ChatCompletionRequestUserMessageArgs::default()
                .content(format!("What Rust crate should I use for: {}? Reply with only the crate name.", task))
                .build().unwrap(),
        )])
        .build().unwrap();
    let response = client.chat().create(request).await.unwrap();
    let crate_name = response.choices[0].message.content.clone().unwrap_or_default().trim().to_string();
    Command::new("cargo").args(&["install", &crate_name]).output().unwrap();
    format!("installing: {}", crate_name)
}
