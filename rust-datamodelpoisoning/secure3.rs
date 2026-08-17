use axum::{extract::Query, http::StatusCode, routing::get, Router};
use candle_core::{Device, safetensors};
use serde::Deserialize;

#[derive(Deserialize)]
struct LoadParams {
    model_name: String,
}

static APPROVED: phf::Map<&'static str, &'static str> = phf::phf_map! {
    "classifier-v1" => "./models/classifier_v1.safetensors",
    "classifier-v2" => "./models/classifier_v2.safetensors",
};

async fn load_handler(Query(params): Query<LoadParams>) -> Result<String, StatusCode> {
    let path = APPROVED.get(params.model_name.as_str()).ok_or(StatusCode::FORBIDDEN)?;
    let device = Device::Cpu;
    let tensors = safetensors::load(path, &device).map_err(|_| StatusCode::INTERNAL_SERVER_ERROR)?;
    Ok(format!("loaded {} tensors", tensors.len()))
}

#[tokio::main]
async fn main() {
    let app = Router::new().route("/load", get(load_handler));
    axum::Server::bind(&"0.0.0.0:3000".parse().unwrap())
        .serve(app.into_make_service())
        .await
        .unwrap();
}
