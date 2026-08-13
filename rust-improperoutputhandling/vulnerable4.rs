use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};

async fn process_task(task: &str) -> Result<(), Box<dyn std::error::Error>> {
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o-mini")
        .messages([ChatCompletionRequestUserMessageArgs::default()
            .content(task)
            .build()?])
        .build()?;
    let response = client.chat().create(request).await?;
    let output = response.choices[0].message.content.clone().unwrap_or_default();
    println!("{}", output);
    Ok(())
}
