use tokio::process::Command;

async fn convert(Json(body): Json<ConvertRequest>) -> Result<(), Error> {
    Command::new("bash").arg("-c").arg(format!("convert {} output.png", body.filename)).status().await?;
    Ok(())
}
