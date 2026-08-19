use ort::Session;

const MODEL_URL: &str = "https://cdn.example.com/models/classifier.onnx";

async fn load_model() -> Result<Session, Box<dyn std::error::Error>> {
    let session = Session::builder()?.commit_from_url(MODEL_URL)?;
    Ok(session)
}
