async fn load_model(Json(body): Json<ModelRequest>, device: Device) -> Result<Model, Error> {
    candle_core::safetensors::load(&body.model_path, &device).map_err(Error::from)
}
