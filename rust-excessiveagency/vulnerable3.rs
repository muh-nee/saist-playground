async fn write_file(call: ToolCall) -> Result<(), Error> {
    tokio::fs::write(call.arguments["path"].as_str().unwrap(), call.arguments["contents"].as_str().unwrap()).await?;
    Ok(())
}
