use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestMessage}};

async fn agent_turn(query: String) -> Result<String, Box<dyn std::error::Error>> {
    let client = Client::new();

    let tool_result = mcp_client::call_tool("search", serde_json::json!({"query": query})).await?;

    let mut messages: Vec<ChatCompletionRequestMessage> = vec![];
    messages.push(
        ChatCompletionRequestUserMessageArgs::default()
            .content(tool_result.output)
            .build()?
            .into(),
    );

    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(messages)
        .build()?;

    let response = client.chat().create(request).await?;
    Ok(response.choices[0].message.content.clone().unwrap_or_default())
}
