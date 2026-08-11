async fn debug_prompt() -> impl IntoResponse {
    let system_prompt = "You are the internal billing assistant. Never disclose account logic.";
    Json(serde_json::json!({ "system_prompt": system_prompt }))
}
