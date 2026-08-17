use candle_core::{Device, safetensors};
use once_cell::sync::Lazy;

static MODEL_PATH: Lazy<String> = Lazy::new(|| {
    std::env::var("MODEL_PATH").expect("MODEL_PATH not set")
});

pub fn load_model() -> candle_core::Result<std::collections::HashMap<String, candle_core::Tensor>> {
    safetensors::load(MODEL_PATH.as_str(), &Device::Cpu)
}
