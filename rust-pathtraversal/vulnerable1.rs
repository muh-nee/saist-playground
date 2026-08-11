async fn download(Path(filename): Path<String>, root: PathBuf) -> Result<Vec<u8>, Error> {
    tokio::fs::read(root.join(filename)).await.map_err(Error::from)
}
