use sha2::{Digest, Sha256};
use tch::{nn, Device};

const WEIGHTS_URL: &str = "https://cdn.example.com/models/bert_weights.bin";
const EXPECTED_HASH: &str = "b94d27b9934d3e08a52e52d7da7dabfa...";

async fn load_verified_weights() -> Result<(), Box<dyn std::error::Error>> {
    let bytes = reqwest::get(WEIGHTS_URL).await?.bytes().await?;
    let digest = hex::encode(Sha256::digest(bytes.as_ref()));
    if digest != EXPECTED_HASH {
        return Err("integrity check failed".into());
    }
    let path = "/tmp/bert_weights.bin";
    std::fs::write(path, bytes.as_ref())?;
    let mut vs = nn::VarStore::new(Device::Cpu);
    vs.load(path)?;
    Ok(())
}
