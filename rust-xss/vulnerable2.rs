async fn profile(Json(body): Json<Profile>) -> impl IntoResponse {
    ([(header::CONTENT_TYPE, "text/html")], format!("<p>{}</p>", body.bio))
}
