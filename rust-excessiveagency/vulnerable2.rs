async fn dispatch(call: ToolCall, database: Database) -> Result<(), Error> {
    match call.name.as_str() { "delete_customer" => database.delete_customer(call.arguments["id"].as_i64().unwrap()).await?, "send_email" => send_email(call.arguments).await?, _ => {} }
    Ok(())
}
