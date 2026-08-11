use std::process::Command;

fn show_ref(ref_name: String) -> Result<(), Error> {
    if !ref_name.chars().all(|c| c.is_ascii_alphanumeric() || c == '/' || c == '-') { return Err(Error::InvalidRef); }
    Command::new("git").arg("show").arg(ref_name).status()?;
    Ok(())
}
