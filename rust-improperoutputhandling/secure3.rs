use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use regex::Regex;

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
    let re = Regex::new(r"\x1b(?:\[[0-?]*[ -/]*[@-~]|\][^\x07\x1b]*(?:\x07|\x1b\\))").unwrap();
    let clean = re.replace_all(&output, "").into_owned();
    println!("{}", clean);
    Ok(())
}
