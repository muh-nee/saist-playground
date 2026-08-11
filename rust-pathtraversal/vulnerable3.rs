fn remove(Query(query): Query<FileQuery>, root: PathBuf) -> Result<(), Error> {
    std::fs::remove_file(root.join(query.name.replace("../", "")))?;
    Ok(())
}
