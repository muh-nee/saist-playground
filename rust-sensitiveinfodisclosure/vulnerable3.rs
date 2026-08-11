async fn answer(client: Client) -> Result<String, Error> {
    let config = tokio::fs::read_to_string("/etc/service/config.toml").await?;
    client.chat(format!("Explain this configuration: {config}")).await.map_err(Error::from)
}
