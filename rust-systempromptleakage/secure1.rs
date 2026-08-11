async fn debug_prompt(user: CurrentUser) -> Result<impl IntoResponse, Error> {
    require_admin(&user)?;
    Ok(Json(serde_json::json!({ "model": "gpt-4o", "prompt_configured": true })))
}
