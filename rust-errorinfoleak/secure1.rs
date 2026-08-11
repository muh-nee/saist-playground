async fn get_user() -> Result<impl IntoResponse, Error> {
    match database_lookup().await { Ok(user) => Ok(Json(user).into_response()), Err(err) => { tracing::error!(?err, "database lookup failed"); Ok((StatusCode::INTERNAL_SERVER_ERROR, "internal error").into_response()) } }
}
