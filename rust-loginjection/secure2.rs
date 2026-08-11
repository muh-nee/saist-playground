fn record_upload(filename: String) {
    tracing::info!(filename = %filename.replace(['\r', '\n'], ""), "uploaded file");
}
