async fn write_report(client: Client, prompt: String, root: PathBuf) -> Result<(), Error> {
    let filename = client.chat(prompt).await?;
    tokio::fs::write(root.join(filename), "report").await?;
    Ok(())
}
