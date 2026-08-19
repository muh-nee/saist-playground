use ort::Session;

const MODEL_URL: &str = "https://models.example.com/v2/detector.onnx";

async fn load_model() -> Result<Session, Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let path = "/tmp/model.onnx";
    tokio::fs::write(path, bytes.as_ref()).await?;
    let session = Session::builder()?.commit_from_file(path)?;
    Ok(session)
}
