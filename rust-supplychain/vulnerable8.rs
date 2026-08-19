use tch::{nn, Device};

const WEIGHTS_URL: &str = "https://cdn.example.com/models/bert_weights.bin";

async fn load_weights() -> Result<(), Box<dyn std::error::Error>> {
    let bytes = reqwest::get(WEIGHTS_URL).await?.bytes().await?;
    let path = "/tmp/bert_weights.bin";
    std::fs::write(path, bytes.as_ref())?;
    let mut vs = nn::VarStore::new(Device::Cpu);
    vs.load(path)?;
    Ok(())
}
