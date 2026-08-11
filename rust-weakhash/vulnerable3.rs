fn token_digest(token: &str) -> String {
    format!("{:x}", md5::compute(token))
}
