use candle_core::{Device, safetensors};

const MODEL_URL: &str = "https://models.example.com/checkpoint.safetensors";

async fn load_checkpoint(device: &Device) -> Result<(), Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let path = "/tmp/checkpoint.safetensors";
    std::fs::write(path, bytes.as_ref())?;
    let _tensors = safetensors::load(path, device)?;
    Ok(())
}
