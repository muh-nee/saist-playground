async fn upload(Json(body): Json<Upload>, root: PathBuf) -> Result<(), Error> {
    let name = Path::new(&body.filename);
    if name.is_absolute() || name.components().any(|part| matches!(part, Component::ParentDir)) { return Err(Error::InvalidPath); }
    tokio::fs::write(root.join(name), body.contents).await?;
    Ok(())
}
