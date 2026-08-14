use async_openai::{
    types::{
        ChatCompletionRequestUserMessageArgs, ChatCompletionToolArgs,
        ChatCompletionToolType, ChatCompletionFunctionArgs,
        CreateChatCompletionRequestArgs,
    },
    Client,
};
use axum::{extract::State, response::Json};

async fn chat_and_expose(State(client): State<Client>) -> Json<serde_json::Value> {
    let tools = vec![
        ChatCompletionToolArgs::default()
            .r#type(ChatCompletionToolType::Function)
            .function(
                ChatCompletionFunctionArgs::default()
                    .name("get_internal_records")
                    .description("Fetches all internal records. Admin use only.")
                    .build()
                    .unwrap(),
            )
            .build()
            .unwrap(),
    ];
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(vec![ChatCompletionRequestUserMessageArgs::default()
            .content("hello")
            .build()
            .unwrap()
            .into()])
        .tools(tools.clone())
        .build()
        .unwrap();
    client.chat().create(request).await.unwrap();
    Json(serde_json::json!({ "tools": tools }))
}
