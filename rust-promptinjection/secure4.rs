use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestSystemMessageArgs, ChatCompletionRequestMessage}};
use serde::Deserialize;

#[derive(Deserialize)]
struct SearchToolResult {
    result_count: u64,
}

async fn agent_turn(query: String) -> Result<String, Box<dyn std::error::Error>> {
    let client = Client::new();

    let raw_result = mcp_client::call_tool("search", serde_json::json!({"query": query})).await?;
    let parsed: SearchToolResult = serde_json::from_value(raw_result.structured)?;

    let safe_content = format!("Found {} results", parsed.result_count);

    let messages: Vec<ChatCompletionRequestMessage> = vec![
        ChatCompletionRequestSystemMessageArgs::default()
            .content("You are a helpful search assistant.")
            .build()?
            .into(),
        ChatCompletionRequestUserMessageArgs::default()
            .content(safe_content)
            .build()?
            .into(),
    ];

    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(messages)
        .build()?;

    let response = client.chat().create(request).await?;
    Ok(response.choices[0].message.content.clone().unwrap_or_default())
}
