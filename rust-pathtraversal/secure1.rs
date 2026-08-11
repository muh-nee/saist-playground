async fn download(Path(filename): Path<String>, root: PathBuf) -> Result<Vec<u8>, Error> {
    let candidate = root.join(filename).canonicalize()?;
    let canonical_root = root.canonicalize()?;
    if !candidate.starts_with(canonical_root) { return Err(Error::InvalidPath); }
    tokio::fs::read(candidate).await.map_err(Error::from)
}
