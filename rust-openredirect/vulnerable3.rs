async fn continue_to(Query(query): Query<ReturnQuery>) -> Result<Redirect, Error> {
    if query.return_to.starts_with("https://trusted.example") { return Ok(Redirect::to(&query.return_to)); }
    Err(Error::InvalidRedirect)
}
