async fn delete_customer(user: CurrentUser, call: ToolCall, database: Database) -> Result<(), Error> {
    require_admin(&user)?;
    require_human_approval(&call).await?;
    database.delete_customer(valid_customer_id(&call.arguments)?).await
}
