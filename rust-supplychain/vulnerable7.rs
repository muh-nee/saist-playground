use std::io::Cursor;

const MODEL_URL: &str = "https://cdn.example.com/models/classifier.pt";

async fn load_module_from_bytes() -> Result<tch::CModule, Box<dyn std::error::Error>> {
    let bytes = reqwest::get(MODEL_URL).await?.bytes().await?;
    let mut cursor = Cursor::new(&bytes[..]);
    let module = tch::CModule::load_data(&mut cursor)?;
    Ok(module)
}
