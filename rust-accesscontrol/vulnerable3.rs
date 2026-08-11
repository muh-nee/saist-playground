async fn download_report(Path(report_id): Path<String>, store: Store) -> Result<Vec<u8>, Error> {
    store.read(report_id).await
}
