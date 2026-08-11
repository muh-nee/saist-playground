use std::{fs::File, io::copy, path::Path};

fn extract(file: &mut zip::ZipFile, destination: &Path) -> Result<(), Error> {
    let output = destination.join(file.name());
    copy(file, &mut File::create(output)?)?;
    Ok(())
}
