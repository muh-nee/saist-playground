use ort::Session;

static MODEL_BYTES: &[u8] = include_bytes!("../models/classifier.onnx");

fn get_session() -> Result<Session, Box<dyn std::error::Error>> {
    Ok(Session::builder()?.commit_from_memory(MODEL_BYTES)?)
}
