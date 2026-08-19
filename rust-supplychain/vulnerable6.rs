const MODEL_URL: &str = "https://cdn.example.com/models/resnet50.pt";

async fn load_module() -> Result<tch::CModule, Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let path = "/tmp/resnet50.pt";
    tokio::fs::write(path, bytes.as_ref()).await?;
    let module = tch::CModule::load(path)?;
    Ok(module)
}
