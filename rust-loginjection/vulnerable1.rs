async fn login(headers: HeaderMap) -> Result<(), Error> {
    let user = headers["x-user"].to_str()?;
    tracing::info!("successful login for {user}");
    Ok(())
}
