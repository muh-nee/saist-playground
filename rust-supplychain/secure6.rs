use candle_core::{Device, safetensors};
use once_cell::sync::Lazy;
use std::env;

static MODEL_PATH: Lazy<String> = Lazy::new(|| env::var("MODEL_PATH").expect("MODEL_PATH not set"));

pub fn load_tensors(device: &Device) -> candle_core::Result<std::collections::HashMap<String, candle_core::Tensor>> {
    safetensors::load(MODEL_PATH.as_str(), device)
}
