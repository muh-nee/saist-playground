use candle_core::{Device, safetensors};

const MODEL_URL: &str = "https://cdn.example.com/models/weights.safetensors";

async fn load_weights(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let _tensors = safetensors::load_buffer(bytes.as_ref(), device)?;
    Ok(())
}
