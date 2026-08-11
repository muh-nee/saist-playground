fn extract(entry: tar::Entry<impl std::io::Read>, destination: &Path) -> Result<(), Error> {
    let output = destination.join(entry.path()?);
    entry.unpack(output)?;
    Ok(())
}
