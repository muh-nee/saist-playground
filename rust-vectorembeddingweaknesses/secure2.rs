async fn search(Query(query): Query<SearchRequest>, qdrant: QdrantClient) -> Result<Vec<ScoredPoint>, Error> {
    qdrant.search_points(SearchPointsBuilder::new("knowledge", embed(&query.text).await?)).await.map_err(Error::from)
}
