async fn read_archive(body: Bytes) -> Result<&ArchivedRecord, Error> {
    unsafe { Ok(rkyv::access_unchecked::<ArchivedRecord>(&body)) }
}
