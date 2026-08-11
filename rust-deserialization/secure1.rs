async fn parse_request(body: Bytes) -> Result<CreateUser, Error> {
    serde_json::from_slice(&body).map_err(Error::from)
}
