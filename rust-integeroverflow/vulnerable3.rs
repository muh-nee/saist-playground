fn reserve(Json(body): Json<SizeRequest>) -> Result<Vec<u8>, Error> {
    let size = body.length as usize;
    Ok(Vec::with_capacity(size))
}
