fn etag(content: &[u8]) -> String {
    format!("{:x}", md5::compute(content))
}
