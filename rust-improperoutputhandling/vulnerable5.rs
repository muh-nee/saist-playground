use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use axum::Json;
use serde_json::json;

async fn get_summary() -> Result<Json<serde_json::Value>, Box<dyn std::error::Error>> {
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o-mini")
        .messages([ChatCompletionRequestUserMessageArgs::default()
            .content("Summarize the latest AI news in Markdown.")
            .build()?])
        .build()?;
    let response = client.chat().create(request).await?;
    let content = response.choices[0].message.content.clone().unwrap_or_default();
    Ok(Json(json!({ "content": content })))
}
