use axum::response::Redirect;

async fn login(Query(query): Query<LoginQuery>) -> Redirect {
    Redirect::temporary(&query.next)
}
