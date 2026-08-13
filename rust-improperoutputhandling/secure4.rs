use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use axum::Json;
use pulldown_cmark::{html, Event, Options, Parser, Tag, TagEnd};

fn sanitize_markdown(input: &str) -> String {
    let parser = Parser::new_ext(input, Options::empty());
    let filtered = parser.filter_map(|event| match event {
        Event::Start(Tag::Image { .. }) | Event::End(TagEnd::Image) => None,
        Event::Html(_) | Event::InlineHtml(_) => None,
        other => Some(other),
    });
    let mut output = String::new();
    html::push_html(&mut output, filtered);
    output
}

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
    let sanitized = sanitize_markdown(&content);
    Ok(Json(serde_json::json!({ "content": sanitized })))
}
