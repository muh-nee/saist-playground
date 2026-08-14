use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use regex::Regex;

fn sanitize_before_storage(text: &str) -> String {
    let injection_re = Regex::new(r"(?i)(ignore (all |previous )?instructions?|you are now|system:)").unwrap();
    let control_re = Regex::new(r"<\|[^|]*\|>").unwrap();
    let cleaned = injection_re.replace_all(text, "");
    control_re.replace_all(&cleaned, "").trim().to_string()
}

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

    let sanitized = sanitize_before_storage(&llm_output);
    vector_store::add_documents(vec![sanitized]).await?;

    Ok(())
}
