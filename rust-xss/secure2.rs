use html_escape::encode_text;

async fn profile(Json(body): Json<Profile>) -> Html<String> {
    Html(format!("<p>{}</p>", encode_text(&body.bio)))
}
