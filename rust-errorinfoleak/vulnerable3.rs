fn handler() -> Result<String, Error> {
    internal_operation().map_err(|err| Error::Public(format!("request failed: {err}")))
}
