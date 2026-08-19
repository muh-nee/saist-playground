use hf_hub::api::sync::Api;
use candle_core::{Device, safetensors};

fn load_from_hub(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let api = Api::new()?;
    let path = api.model("org/my-classifier".to_string()).get("model.safetensors")?;
    let _tensors = safetensors::load(&path, device)?;
    Ok(())
}
