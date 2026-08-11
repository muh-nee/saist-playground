async fn import_file(Path(name): Path<String>) -> impl IntoResponse {
    match tokio::fs::read(name).await { Ok(bytes) => (StatusCode::OK, bytes).into_response(), Err(err) => (StatusCode::BAD_REQUEST, format!("import failed: {err:?}")).into_response() }
}
