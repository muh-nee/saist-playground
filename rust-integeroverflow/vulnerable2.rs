fn read_offset(Query(query): Query<OffsetRequest>, data: &[u8]) -> Result<u8, Error> {
    let offset = query.page * query.page_size;
    Ok(data[offset])
}
