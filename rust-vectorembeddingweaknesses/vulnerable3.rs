async fn ingest_url(Query(query): Query<UrlQuery>, qdrant: QdrantClient) -> Result<(), Error> {
    let content = reqwest::get(query.url).await?.text().await?;
    qdrant.upsert_points(UpsertPointsBuilder::new("shared", vec![point(content)])).await?;
    Ok(())
}
