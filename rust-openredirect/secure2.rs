async fn continue_to(Query(query): Query<ReturnQuery>) -> Result<Redirect, Error> {
    let url = Url::parse(&query.return_to)?;
    if url.scheme() != "https" || url.host_str() != Some("trusted.example") { return Err(Error::InvalidRedirect); }
    Ok(Redirect::to(url.as_str()))
}
