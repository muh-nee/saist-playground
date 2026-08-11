use std::{fs::File, io::copy, path::Path};

fn extract(file: &mut zip::ZipFile, destination: &Path) -> Result<(), Error> {
    let relative = file.enclosed_name().ok_or(Error::InvalidArchivePath)?;
    copy(file, &mut File::create(destination.join(relative))?)?;
    Ok(())
}
