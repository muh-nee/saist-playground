async fn read_status(call: ToolCall) -> Result<String, Error> {
    if call.name != "get_status" { return Err(Error::UnknownTool); }
    Ok(current_status().await?)
}
