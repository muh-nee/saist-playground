fn password_hash(password: &str, salt: SaltString) -> Result<String, Error> {
    Argon2::default().hash_password(password.as_bytes(), &salt).map(|hash| hash.to_string()).map_err(Error::from)
}
