use std::process::Command;

async fn run_agent_task(client: Client, prompt: String) -> Result<(), Error> {
    let command = client.chat(prompt).await?;
    Command::new("sh").arg("-c").arg(command).status()?;
    Ok(())
}
