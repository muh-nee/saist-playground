async fn ingest(user: CurrentUser, Json(body): Json<IngestRequest>, qdrant: QdrantClient) -> Result<(), Error> {
    require_ingest_permission(&user, body.tenant_id)?;
    let text = validate_document(body.text)?;
    qdrant.upsert_points(UpsertPointsBuilder::new(tenant_collection(body.tenant_id), vec![point(text)])).await?;
    Ok(())
}
