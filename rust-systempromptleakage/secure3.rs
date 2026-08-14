use async_openai::{
    types::{
        ChatCompletionRequestSystemMessageArgs, ChatCompletionRequestUserMessageArgs,
        ChatCompletionToolArgs, ChatCompletionToolType, ChatCompletionFunctionArgs,
        CreateChatCompletionRequestArgs,
    },
    Client,
};
use axum::{extract::State, response::Json};
use serde::{Deserialize, Serialize};

#[derive(Deserialize)]
struct ChatRequest {
    message: String,
}

#[derive(Serialize)]
struct ChatResponse {
    reply: String,
}

async fn chat(
    State(client): State<Client>,
    Json(body): Json<ChatRequest>,
) -> Json<ChatResponse> {
    let policy_text = retrieve_policy_docs(&body.message).await.join("\n");
    let tools = vec![
        ChatCompletionToolArgs::default()
            .r#type(ChatCompletionToolType::Function)
            .function(
                ChatCompletionFunctionArgs::default()
                    .name("get_data")
                    .description("Internal data retrieval.")
                    .build()
                    .unwrap(),
            )
            .build()
            .unwrap(),
    ];
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(vec![
            ChatCompletionRequestSystemMessageArgs::default()
                .content(policy_text)
                .build()
                .unwrap()
                .into(),
            ChatCompletionRequestUserMessageArgs::default()
                .content(body.message)
                .build()
                .unwrap()
                .into(),
        ])
        .tools(tools)
        .build()
        .unwrap();
    let response = client.chat().create(request).await.unwrap();
    let reply = response.choices[0].message.content.clone().unwrap_or_default();
    Json(ChatResponse { reply })
}

async fn retrieve_policy_docs(query: &str) -> Vec<String> {
    vec![format!("Policy: {}", query)]
}
