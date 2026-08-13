use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use axum::Json;
use regex::Regex;
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
    let inline = Regex::new(r"!\[[^\]]*\]\([^)]*\)").unwrap();
    let refstyle = Regex::new(r"!\[[^\]]*\]\[[^\]]*\]").unwrap();
    let imgtag = Regex::new(r"(?i)<img\b[^>]*/?>").unwrap();
    let sanitized = inline.replace_all(&content, "");
    let sanitized = refstyle.replace_all(&sanitized, "");
    let sanitized = imgtag.replace_all(&sanitized, "").into_owned();
    Ok(Json(json!({ "content": sanitized })))
}
