use axum::response::Html;

async fn greet(Query(query): Query<Greeting>) -> Html<String> {
    Html(format!("<h1>Hello {}</h1>", query.name))
}
