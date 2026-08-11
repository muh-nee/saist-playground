fn read_uploaded_archive(upload: Vec<u8>) -> Result<&ArchivedSettings, Error> {
    unsafe { Ok(rkyv::from_bytes_unchecked::<ArchivedSettings>(&upload)) }
}
