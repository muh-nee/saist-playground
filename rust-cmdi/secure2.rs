use std::process::Command;

fn run_action(action: String) -> Result<(), Error> {
    let command = match action.as_str() { "status" => "status", "version" => "--version", _ => return Err(Error::InvalidAction) };
    Command::new("git").arg(command).status()?;
    Ok(())
}
