fn extract(mut entry: tar::Entry<impl std::io::Read>, destination: &Path) -> Result<(), Error> {
    entry.unpack_in(destination)?;
    Ok(())
}
