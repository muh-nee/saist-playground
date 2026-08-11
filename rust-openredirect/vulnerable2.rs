async fn logout(Query(query): Query<ReturnQuery>) -> impl IntoResponse {
    (StatusCode::FOUND, [(header::LOCATION, query.return_to)] )
}
