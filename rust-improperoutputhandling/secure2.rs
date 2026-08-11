async fn run_agent_task(client: Client, prompt: String) -> Result<(), Error> {
    let result = client.chat(prompt).await?;
    match serde_json::from_str::<Action>(&result)? { Action::Status => std::process::Command::new("git").arg("status").status()?, Action::Version => std::process::Command::new("git").arg("--version").status()? };
    Ok(())
}
