use candle_core::{Device, safetensors};
use sha2::{Digest, Sha256};

const TRUSTED_MODEL_URL: &str = "https://internal.example.com/models/classifier.safetensors";
const EXPECTED_SHA256: &str = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

pub async fn load_verified_model() -> candle_core::Result<std::collections::HashMap<String, candle_core::Tensor>> {
    let bytes = reqwest::get(TRUSTED_MODEL_URL)
        .await
        .unwrap()
        .bytes()
        .await
        .unwrap();
    let digest = format!("{:x}", Sha256::digest(&bytes));
    assert_eq!(digest, EXPECTED_SHA256, "integrity check failed");
    let device = Device::Cpu;
    safetensors::load_buffer(&bytes, &device)
}
