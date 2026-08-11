fn reset_token() -> String {
    (0..32).map(|_| fastrand::alphanumeric()).collect()
}
