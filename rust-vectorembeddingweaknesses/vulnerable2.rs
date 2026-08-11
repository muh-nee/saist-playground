async fn add_document(Json(body): Json<Document>, store: VectorStore) -> Result<(), Error> {
    store.add_texts(vec![body.content]).await?;
    Ok(())
}
