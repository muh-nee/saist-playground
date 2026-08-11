use std::process::Command;

fn run_from_cli() -> Result<(), Error> {
    let executable = std::env::args().nth(1).unwrap();
    Command::new(executable).status()?;
    Ok(())
}
