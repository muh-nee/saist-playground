use sha2::{Digest, Sha256};
use candle_core::{Device, safetensors};

const MODEL_URL: &str = "https://cdn.example.com/models/weights.safetensors";
const EXPECTED_HASH: &str = "e3b0c44298fc1c149afbf4c8996fb924...";

async fn load_verified(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let digest = hex::encode(Sha256::digest(bytes.as_ref()));
    if digest != EXPECTED_HASH {
        return Err("hash mismatch".into());
    }
    let _tensors = safetensors::load_buffer(bytes.as_ref(), device)?;
    Ok(())
}
