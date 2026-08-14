use async_openai::{
    types::{
        ChatCompletionRequestSystemMessageArgs, ChatCompletionRequestUserMessageArgs,
        CreateChatCompletionRequestArgs,
    },
    Client,
};
use axum::{extract::{Query, State}, response::Json};
use qdrant_client::{client::QdrantClient, qdrant::SearchPoints};
use std::collections::HashMap;

async fn context_handler(
    State((client, qdrant)): State<(Client, QdrantClient)>,
    Query(params): Query<HashMap<String, String>>,
) -> Json<serde_json::Value> {
    let query = params.get("q").cloned().unwrap_or_default();
    let search_result = qdrant
        .search_points(&SearchPoints {
            collection_name: "policy-index".to_string(),
            limit: 3,
            ..Default::default()
        })
        .await
        .unwrap();
    let policy_text = search_result
        .result
        .iter()
        .filter_map(|p| p.payload.get("text").and_then(|v| v.as_str()).map(String::from))
        .collect::<Vec<_>>()
        .join("\n");
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o")
        .messages(vec![
            ChatCompletionRequestSystemMessageArgs::default()
                .content(policy_text.clone())
                .build()
                .unwrap()
                .into(),
            ChatCompletionRequestUserMessageArgs::default()
                .content(query)
                .build()
                .unwrap()
                .into(),
        ])
        .build()
        .unwrap();
    client.chat().create(request).await.unwrap();
    Json(serde_json::json!({ "policy": policy_text }))
}
