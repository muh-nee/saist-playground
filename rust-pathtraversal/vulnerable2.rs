async fn upload(Json(body): Json<Upload>, root: PathBuf) -> Result<(), Error> {
    tokio::fs::write(root.join(body.filename), body.contents).await?;
    Ok(())
}
