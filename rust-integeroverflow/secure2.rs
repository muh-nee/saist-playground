fn reserve(Json(body): Json<SizeRequest>) -> Result<Vec<u8>, Error> {
    let size = usize::try_from(body.length).map_err(|_| Error::InvalidSize)?;
    if size > MAX_ALLOCATION { return Err(Error::InvalidSize); }
    Ok(Vec::with_capacity(size))
}
