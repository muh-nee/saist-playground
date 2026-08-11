async fn import_file(Path(name): Path<String>) -> impl IntoResponse {
    if let Err(err) = tokio::fs::read(name).await { tracing::warn!(?err, "import failed"); return (StatusCode::BAD_REQUEST, "invalid import").into_response(); }
    StatusCode::NO_CONTENT.into_response()
}
