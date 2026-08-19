use ort::Session;

const MODEL_URL: &str = "https://cdn.example.com/models/classifier.onnx";

async fn load_model() -> Result<Session, Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let session = Session::builder()?.commit_from_memory(bytes.as_ref())?;
    Ok(session)
}
