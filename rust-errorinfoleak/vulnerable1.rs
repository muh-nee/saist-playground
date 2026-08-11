async fn get_user() -> Result<impl IntoResponse, Error> {
    match database_lookup().await { Ok(user) => Ok(Json(user).into_response()), Err(err) => Ok((StatusCode::INTERNAL_SERVER_ERROR, err.to_string()).into_response()) }
}
