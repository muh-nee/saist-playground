use actix_web::{post, web, HttpResponse};
use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use serde::Deserialize;

#[derive(Deserialize)]
struct SummarizeRequest {
    query: String,
    session_id: String,
}

#[post("/summarize")]
async fn store_summary(req: web::Json<SummarizeRequest>) -> HttpResponse {
    let client = Client::new();

    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages([ChatCompletionRequestUserMessageArgs::default()
            .content(req.query.clone())
            .build()
            .unwrap()
            .into()])
        .build()
        .unwrap();

    let response = client.chat().create(request).await.unwrap();
    let llm_output = response.choices[0].message.content.clone().unwrap_or_default();

    vector_store::add_documents(vec![llm_output], &req.session_id).await.unwrap();

    HttpResponse::Ok().json(serde_json::json!({ "stored": true }))
}
