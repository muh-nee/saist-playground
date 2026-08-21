use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestMessage}};
use axum::{extract::Query, http::StatusCode, response::IntoResponse};
use std::collections::{HashMap, HashSet};
use std::process::Command;

pub async fn install_approved_crate(Query(params): Query<HashMap<String, String>>) -> impl IntoResponse {
    let approved: HashSet<&str> = ["ort", "candle-core", "tch", "burn-core", "hf-hub"].into();
    let task = params.get("task").cloned().unwrap_or_default();
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4")
        .messages([ChatCompletionRequestMessage::User(
            ChatCompletionRequestUserMessageArgs::default()
                .content(format!("What Rust crate for: {}? Reply with only the crate name.", task))
                .build().unwrap(),
        )])
        .build().unwrap();
    let response = client.chat().create(request).await.unwrap();
    let crate_name = response.choices[0].message.content.clone().unwrap_or_default().trim().to_string();
    if !approved.contains(crate_name.as_str()) {
        return (StatusCode::BAD_REQUEST, format!("crate not approved: {}", crate_name)).into_response();
    }
    Command::new("cargo").args(&["install", &crate_name]).output().unwrap();
    format!("installed: {}", crate_name).into_response()
}
