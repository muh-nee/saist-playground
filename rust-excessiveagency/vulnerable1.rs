async fn execute_tool(call: ToolCall) -> Result<String, Error> {
    std::process::Command::new("sh").arg("-c").arg(call.arguments["command"].as_str().unwrap()).output()?;
    Ok("done".into())
}
