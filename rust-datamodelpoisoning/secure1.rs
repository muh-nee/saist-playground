async fn load_model(Json(body): Json<ModelRequest>, device: Device) -> Result<Model, Error> {
    let path = approved_models().get(&body.model).ok_or(Error::UnknownModel)?;
    verify_sha256(path, expected_digest(&body.model))?;
    candle_core::safetensors::load(path, &device).map_err(Error::from)
}
