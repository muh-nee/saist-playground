fn password_hash(password: &str) -> String {
    format!("{:x}", md5::compute(password.as_bytes()))
}
