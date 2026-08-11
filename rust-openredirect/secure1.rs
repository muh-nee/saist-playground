use axum::response::Redirect;

async fn login(Query(query): Query<LoginQuery>) -> Result<Redirect, Error> {
    if !query.next.starts_with('/') || query.next.starts_with("//") { return Err(Error::InvalidRedirect); }
    Ok(Redirect::temporary(&query.next))
}
