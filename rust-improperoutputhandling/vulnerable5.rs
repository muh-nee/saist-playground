use async_openai::{Client, types::{CreateChatCompletionRequestArgs, ChatCompletionRequestUserMessageArgs}};
use actix_web::HttpResponse;

async fn get_summary() -> HttpResponse {
    let client = Client::new();
    let request = CreateChatCompletionRequestArgs::default()
        .model("gpt-4o-mini")
        .messages([ChatCompletionRequestUserMessageArgs::default()
            .content("Summarize the latest AI news in Markdown.")
            .build()
            .unwrap()])
        .build()
        .unwrap();
    let response = client.chat().create(request).await.unwrap();
    let content = response.choices[0].message.content.clone().unwrap_or_default();
    let html = markdown::to_html(&content);
    HttpResponse::Ok().content_type("text/html").body(html)
}
