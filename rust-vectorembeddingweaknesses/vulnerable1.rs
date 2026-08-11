async fn ingest(Json(body): Json<IngestRequest>, qdrant: QdrantClient) -> Result<(), Error> {
    let point = PointStruct::new(body.id, embed(&body.text).await?, json!({"text": body.text}));
    qdrant.upsert_points(UpsertPointsBuilder::new("knowledge", vec![point])).await?;
    Ok(())
}
