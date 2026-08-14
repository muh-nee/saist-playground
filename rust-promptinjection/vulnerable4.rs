use async_openai::{Client, types::CreateChatCompletionRequestArgs, types::ChatCompletionRequestUserMessageArgs};

async fn store_summary(query: String) -> Result<(), Box<dyn std::error::Error>> {
    let client = Client::new();

    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages([ChatCompletionRequestUserMessageArgs::default()
            .content(query)
            .build()?
            .into()])
        .build()?;

    let response = client.chat().create(request).await?;
    let llm_output = response.choices[0].message.content.clone().unwrap_or_default();

    vector_store::add_documents(vec![llm_output]).await?;

    Ok(())
}
