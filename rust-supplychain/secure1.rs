use sha2::{Digest, Sha256};
use ort::Session;

const MODEL_URL: &str = "https://cdn.example.com/models/classifier.onnx";
const EXPECTED_HASH: &str = "a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2c3d4e5f6a1b2";

async fn load_verified() -> Result<Session, Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let digest = hex::encode(Sha256::digest(bytes.as_ref()));
    if digest != EXPECTED_HASH {
        return Err("integrity check failed".into());
    }
    Ok(Session::builder()?.commit_from_memory(bytes.as_ref())?)
}

