use std::process::Command;

async fn run(Query(request): Query<RunRequest>) -> Result<(), Error> {
    Command::new("sh").arg("-c").arg(request.command).status()?;
    Ok(())
}
