async fn allocate(Json(body): Json<AllocationRequest>) -> Result<Vec<u8>, Error> {
    let bytes = body.count.checked_mul(body.element_size).filter(|bytes| *bytes <= MAX_ALLOCATION).ok_or(Error::InvalidSize)?;
    Ok(vec![0; bytes])
}
