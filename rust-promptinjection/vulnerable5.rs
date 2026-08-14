use actix_web::{post, web, HttpResponse};
use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs, ChatCompletionRequestMessage}};
use serde::Deserialize;

#[derive(Deserialize)]
struct AgentRequest {
    query: String,
    messages: Vec<serde_json::Value>,
}

#[post("/agent")]
async fn agent_turn(req: web::Json<AgentRequest>) -> HttpResponse {
    let client = Client::new();

    let tool_result = mcp_client::call_tool("web_search", serde_json::json!({"query": req.query})).await.unwrap();

    let mut messages: Vec<ChatCompletionRequestMessage> = vec![];
    messages.push(
        ChatCompletionRequestUserMessageArgs::default()
            .content(tool_result.output)
            .build()
            .unwrap()
            .into(),
    );

    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(messages)
        .build()
        .unwrap();

    let response = client.chat().create(request).await.unwrap();
    let reply = response.choices[0].message.content.clone().unwrap_or_default();

    HttpResponse::Ok().json(serde_json::json!({ "reply": reply }))
}
