async fn allocate(Json(body): Json<AllocationRequest>) -> Result<Vec<u8>, Error> {
    let bytes = body.count * body.element_size;
    Ok(vec![0; bytes])
}
