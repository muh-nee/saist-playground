async fn extract_uploaded(mut archive: zip::ZipArchive<File>, destination: PathBuf) -> Result<(), Error> {
    let mut entry = archive.by_index(0)?;
    tokio::fs::write(destination.join(entry.name()), read_all(&mut entry)?).await?;
    Ok(())
}
